package com.cobaltumapps.simplecalculator.fragments

import Calculator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.activities.ConverterActivity
import com.cobaltumapps.simplecalculator.activities.OptionsActivity
import com.cobaltumapps.simplecalculator.adapters.NumPadPagerAdapter
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.Memory
import com.cobaltumapps.simplecalculator.references.Services
import java.math.BigDecimal
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.min


class CalculatorFragment: Fragment() {
    private var calculateResult: Double = 0.0                                       // Результат подсчёта
    private lateinit var numpadPager: ViewPager2
    private lateinit var pagerAdapter: NumPadPagerAdapter

    // Логика
    private var canPointEnter = true                                                // Возможность вводить точку
    private var memoryAutoSaveResult = false                                        // Автосохранение в память
    var leftHandMode: Boolean = false                                               // Режим левой руки
    private var miniMode: Boolean = false                                           // Состояние мини-клавиатуры (true/false)
    private var miniModePreference: Boolean = false
    private var vibrationPreference: Boolean = false

    // References
    private val calculator: Calculator = Calculator() // Объект калькулятора
    private val displayFragment: DisplayFragment = DisplayFragment()                // Фрагмент дисплея калькулятора
    private val numpadFragment: NumpadFragment = NumpadFragment()                   // Фрагмент обычной клавиатуры
    private val engineeringFragment: EngineeringFragment = EngineeringFragment()    // Фрагмент научной клавиатуры
    private val memory: Memory by lazy { Memory() }                                 // Объект памяти

    private lateinit var sharedPreferences: SharedPreferences                       // Хранилище

    // Кнопки "иконки"
    private lateinit var optionsButton: ImageView     // Открывает настройки
    private lateinit var miniModeButton: ImageView     // Включает мини-режим
    private lateinit var convertersButton: ImageView     // Иконка бекспейса
    private lateinit var backspaceIconButton: ImageView     // Иконка бекспейса
    private lateinit var clearAllAlternativeButton: ImageView     // Иконка бекспейса

    private lateinit var displayCard: com.google.android.material.card.MaterialCardView

    private lateinit var titleMiniMode: TextView

    private var expressionResult: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getPreferenceObject()                                   // Получение объекта

        // Передаёт ссылку на себя для доступа к фрагменту
        numpadFragment.setParentFragment(this@CalculatorFragment)
        engineeringFragment.setParentFragment(this@CalculatorFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false) // View фрагмента
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Настройка пейджера для клавиатуры

        displayCard = view.findViewById(R.id.displayCard)
        numpadPager = view.findViewById(R.id.numpadPager) as ViewPager2

        optionsButton = view.findViewById(R.id.buttonOptions)                         // Ссылка на кнопку "options"
        miniModeButton = view.findViewById(R.id.buttonMiniMode)                       // Ссылка на кнопку "miniMode"
        backspaceIconButton = view.findViewById(R.id.buttonBackspace)                 // Ссылка на кнопку "backspace"
        convertersButton = view.findViewById(R.id.buttonConverters)                   // Ссылка на кнопку "converters"
        clearAllAlternativeButton = view.findViewById(R.id.buttonClearAllAlternative)

        backspaceIconButton.alpha = 0f

        pagerAdapter = NumPadPagerAdapter(requireActivity(), arrayListOf(numpadFragment,engineeringFragment))
        numpadPager.adapter = pagerAdapter
        numpadPager.offscreenPageLimit = 1 // Предзагрузка  страницы пейджера

        // Анимация
        numpadPager.setPageTransformer { page, pos: Float ->
            val height = page.height.toFloat()
            val width = page.width.toFloat()
            val scale: Float = min(if (pos < 0) 1f else abs(1f - pos), 1f)
            page.scaleX = scale
            page.scaleY = scale
            page.pivotX = width * 0.5f
            page.pivotY = height * 0.5f
            page.translationX = if (pos < 0) width * pos else -width * pos * 0.25f
        }

        numpadPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val progress = position + positionOffset
                backspaceIconButton.alpha = progress
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                backspaceIconButton.isEnabled = position == 1
            }
        })


        titleMiniMode = view.findViewById(R.id.miniModeTitle)
        titleMiniMode.alpha = 0f

        // Заменяет пустоту на displayFragment
        parentFragmentManager.beginTransaction().replace(R.id.displayCard, displayFragment).commit()

        // Иконки настроек и истории
        optionsButton.setOnClickListener { startActivity(Intent(requireContext(), OptionsActivity::class.java));  } // Option Icon
        miniModeButton.setOnClickListener { miniModeSet(miniMode); miniMode = !miniMode
            Animations.playAnimation(requireActivity(),miniModeButton,R.anim.scale_out,Animations.overshootInterpolator) } // MiniMode Icon

        backspaceIconButton.setOnClickListener { backSpaceAction(); playVibration(5) }
        backspaceIconButton.setOnLongClickListener { clearAllAction(); playVibration(20); true }

        clearAllAlternativeButton.setOnClickListener { clearAllAction(); playVibration(20); }

        convertersButton.setOnClickListener { startActivity(Intent(requireActivity(),ConverterActivity::class.java)) }

    }

    override fun onResume() {
        super.onResume()
        setTypeAngle(false)
        // После возобновлении фрагмента, получает из настроек ключи и обновляет перменные согласно ключам
        getPreferences(sharedPreferences) // Получение сохраненных настроек

        miniMode = miniModePreference

        if (miniModePreference){
            miniModeButton.isEnabled = true
            miniModeButton.alpha = 1f
            miniModeButton.rotation = -45f
        }
        else{
            miniModeButton.isEnabled = false
            miniModeButton.alpha = 0f
        }
    }

    override fun onPause() {
        super.onPause()
        // Возвращает клавиатуру в обычный режим, если включён минимод
        if (miniModePreference) {
            miniMode = false
            miniModeSet(false)
        }
    }


    fun playVibration(duration: Long){
        if (vibrationPreference) Services.playVibration(requireContext(), duration)
    }


    // required review                                                      --------------------------------------------------------------------------------
    private fun miniModeSet(reduce: Boolean) {
        val duration: Long = 400
        val startScale: Float
        val endScale: Float
        val startAlpha: Float
        val endAlpha: Float
        val pivotX: Float
        val pivotY: Float

        if (reduce) {
            startScale = 1f
            endScale = 0.75f
            startAlpha = 0f
            endAlpha = 0.7f
            pivotX = if (!leftHandMode) numpadPager.width.toFloat() else 0f
            pivotY = numpadPager.height.toFloat()
        } else {
            startScale = 0.75f
            endScale = 1f
            startAlpha = 0.7f
            endAlpha = 0f
            pivotX = if (!leftHandMode) numpadPager.width.toFloat() else 0f
            pivotY = numpadPager.height.toFloat()
        }

        miniModeButton.setImageDrawable(ResourcesCompat.getDrawable(resources, if (reduce) R.drawable.baseline_unfold_more_32 else R.drawable.baseline_unfold_less_32, resources.newTheme())) // Смена иконки мини-режима

        Animations.animatePropertyChange(titleMiniMode, "alpha", startAlpha, endAlpha, duration, Animations.overshootInterpolator)
        numpadPager.pivotX = pivotX
        numpadPager.pivotY = pivotY
        viewScaling(numpadPager, startScale, endScale, duration)
    }


    private fun viewScaling(view: View,scaleFrom: Float = 0.75f, scaleTo: Float = 1f,duration: Long = 300){
        Animations.animatePropertyChange(view,"scaleX",scaleFrom,scaleTo,duration, Animations.overshootInterpolator)
        Animations.animatePropertyChange(view,"scaleY",scaleFrom,scaleTo,duration, Animations.overshootInterpolator)

    }

    fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        displayFragment.enterToExpression(numberSymbol)
        calculateExpression()
    }

    fun operationEnterAction(operator: String,ignoreAutoRechange: Boolean = false) // Обработчик нажатия - оператора (+ - * /)
    {
        val input = displayFragment.getInputStringBuilder()
        val lastSymbol = input.last()

        if (!ignoreAutoRechange){

            if (lastSymbol in setOf(
                    ConstantsCalculator.symbolAdd,ConstantsCalculator.symbolSub,
                    ConstantsCalculator.symbolMul,ConstantsCalculator.symbolDiv,
                    ConstantsCalculator.symbolPower,ConstantsCalculator.symbolPoint,ConstantsCalculator.symbolFactorial))
            {
                if (lastSymbol != ConstantsCalculator.symbolSqrt)
                    backSpaceAction()
            }
            if (lastSymbol != ConstantsCalculator.symbolSqrt){
                displayFragment.enterToExpression(operator)
            }
        }
        else{
            displayFragment.enterToExpression(operator)
        }
        canPointEnter = true
        calculateExpression()
    }

    fun sqrtAction() { // Добавляет знак квадратного корня
        operationEnterAction("${ConstantsCalculator.symbolSqrt}")
    }

    fun powerAction() { // Добавляет знак возведения в степень
        operationEnterAction("${ConstantsCalculator.symbolPower}")
    }

    fun percentAction() { // Добавляет знак процента в поле ввода
        operationEnterAction("${ConstantsCalculator.symbolPercent}")
    }

    fun factorialAction() {
        operationEnterAction("${ConstantsCalculator.symbolFactorial}")
    }

    fun invertAction() {
        displayFragment.setInputField( calculator.closeExpressionString(displayFragment.getInputField() ))
        calculateExpression()
    }

    fun enterTrigonometryFunc(dataSetId: Int){
        if (displayFragment.textFields[0].text.last() !in setOf(ConstantsCalculator.symbolAdd,ConstantsCalculator.symbolSub,
                ConstantsCalculator.symbolMul,ConstantsCalculator.symbolDiv,
                ConstantsCalculator.symbolPower))
        {
            displayFragment.enterToExpression(getString(R.string.symbolMul)) // Ставит умножение, если нет других операторв
        }

        when(dataSetId){
            0 -> {operationEnterAction(getString(R.string.symbolSin) + "(",true)}
            1 -> {operationEnterAction(getString(R.string.symbolCos) + "(",true)}
            2 -> {operationEnterAction(getString(R.string.symbolTan) + "(",true)}
            3 -> {operationEnterAction(getString(R.string.symbolCot) + "(",true)}
            4 -> {operationEnterAction(getString(R.string.symbolLog) + "(",true)}
            5 -> {operationEnterAction(getString(R.string.symbolLn) + "(",true)}
        }
    }

    fun enterPointAction() // Ввод точки
    {
        // После ввода - отключает возможность повторно ввести
        if (canPointEnter){
            displayFragment.enterToExpression(ConstantsCalculator.symbolPoint.toString())
            canPointEnter = false
        }
    }

    // Расчитывает результат и возвращает его
    private fun calculateAction(): Double{
        // Получение введённого выражения
        val expression = displayFragment.textFields[0].text.toString()

        // Объявление калькулятора и передача выражения
        val calculator = Calculator(expression)

        // Вызов метода "calculate" и расчёт результата
        calculator.calculate(true)

        return calculator.getResult()
    }

    fun calculateExpression() // Событие вычислена результата
    {
        try {
            // Расчёт результата и запись в основное поле и запись результата в переменную
            displayFragment.checkTextFields()

            calculateResult = calculateAction()

            // Если результат вычислений - бесконечность
            if (calculateResult.isInfinite())
                displayFragment.setResultField(resources.getString(R.string.error_infinite))

            // Если результат вычислений - не число
            if (calculateResult.isNaN())
                displayFragment.setResultField(resources.getString(R.string.error_nan))

            else{
                val result = StringBuilder(calculateResult.toString())
                val format = DecimalFormat("#.###########")
                val bigDecimal = BigDecimal(calculateResult)

                if (result[result.lastIndex - 1] == '.' && result[result.lastIndex] == '0'){ // Если в ответе последний символ после точки = 0 - значит число является типом Int
                    displayFragment.setResultField(
                        format.format(bigDecimal).toString()) // Вывод в Int
                }
                else{
                    displayFragment.setResultField(format.format(bigDecimal).toString().replace(',', '.')) // Вывод в Double с заменой запятой на точку

                }
                expressionResult = calculateResult.toString()
                // Если автосохранение включено
                if(memoryAutoSaveResult){
                    memorySave()
                }
            }

        }
        catch (ex: Exception){
            Log.e("DebugTag",ex.toString())
        }
    }

    fun backSpaceAction() {
        try {
            val deletedLastSymbol = displayFragment.clearLastSymbolFromExpression(displayFragment.textFields[0])

            // Если удалённый символ - '.' (точка)
            if (deletedLastSymbol == '.')
                canPointEnter = true

            calculateExpression()
        } catch (ex: Exception) {
            errorHandler()
        }
    }

    fun clearAllAction() // Обработчик нажатия - очистка полей
    {
        displayFragment.clearTextField(
            inputField = true,
            resultField = true,
            memoryField = false,
            lastField = false)

        canPointEnter = true
    }

    fun clearGroupAction() {
        displayFragment.textFields[0].text = removeDigitsFromEnd(displayFragment.textFields[0].text.toString())
        calculateExpression()
    }

    private fun removeDigitsFromEnd(input: String): String { // Удаляет число цифр из строки
        val stringBuilder = StringBuilder(input)
        if (input.isNotEmpty()) {
            var index = stringBuilder.length - 1

            if (!stringBuilder[index].isDigit()) {
                stringBuilder.deleteCharAt(index)
            }
            else{
                // Находим первый символ, который не является цифрой
                while (index >= 0 && stringBuilder[index].isDigit()) {
                    stringBuilder.deleteCharAt(index)
                    index--
                }
            }
        }
        return stringBuilder.toString()
    }

    // Работа с памятью
    fun memorySave(){
        if (expressionResult.isNotEmpty()){
            memory.save(expressionResult.toDouble())
            displayFragment.setMemoryField(memory.read().toString())
        }
    }

    fun memoryRead(){
        if (expressionResult.isNotEmpty()){
            val readMemory = memory.read()
            operationEnterAction(getString(R.string.symbolMul))
            displayFragment.enterToExpression(readMemory.toString())
            calculateExpression()
        }
    }

    fun memoryClear() {
        memory.clear()
        displayFragment.clearTextField(inputField = false, resultField = false, memoryField = true)
    }

    fun memoryResultAdd(){
        memory.addToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayFragment.setMemoryField(readMemory.toString())
    }

    fun memoryResultSub(){
        memory.subToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayFragment.setMemoryField(readMemory.toString())
    }
    fun memoryResultMul(){
        memory.mulToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayFragment.setMemoryField(readMemory.toString())
    }
    fun memoryResultDiv(){
        memory.divToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayFragment.setMemoryField(readMemory.toString())
    }

    fun pushExpressionToLast(){
        if(expressionResult.isNotEmpty()){
            displayFragment.enterToLastExpression()
            displayFragment.setInputField(displayFragment.textFields[1].text.toString().replace(" ",""))
            calculateExpression()
        }
    }

    private fun errorHandler() // Обработчик ошибки
    {
        displayFragment.setResultField(resources.getString(R.string.error_failCalculate))
    }

    // Preferences
    private fun getPreferenceObject(): SharedPreferences {
        return requireContext().getSharedPreferences(
            ConstantsCalculator.vault,
            Context.MODE_PRIVATE
        )
    }

    private fun getPreferences(sharedPreferences: SharedPreferences) {
        memoryAutoSaveResult = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[0], false)
        leftHandMode = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[1], false)
        miniModePreference = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[2], false)
        vibrationPreference = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[3], true)
    }

    fun setTypeAngle(isDegree: Boolean) {
        displayFragment.updateAngleType(isDegree)
        calculator.setRadiansState(isDegree)
        Log.i("DebugTag",calculator.getRadiansState().toString())
        calculateExpression()
    }

}