package com.cobaltumapps.simplecalculator.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.activities.OptionsActivity
import com.cobaltumapps.simplecalculator.adapters.NumPadPagerAdapter
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.references.CalculatorCore
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.Memory
import com.cobaltumapps.simplecalculator.references.Vibration
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
    private var roundRangePreference: Int = 3
    private var miniModePreference: Boolean = false
    private var vibrationPreference: Boolean = false

    // References
    private val displayFragment: DisplayFragment = DisplayFragment()
    private val numpadFragment: NumpadFragment = NumpadFragment()
    private val engineeringFragment: EngineeringFragment = EngineeringFragment()
    private val memory: Memory by lazy { Memory() }                                 // Объект памяти
    private lateinit var sharedPreferences: SharedPreferences                       // Объект предпочтений

    // Кнопки "иконки"
    private lateinit var optionsButton: ImageButton     // Открывает настройки
    private lateinit var miniModeButton: ImageButton     // Включает мини-режим
    private lateinit var displayCard: com.google.android.material.card.MaterialCardView

    private lateinit var titleMiniMode: TextView
    private lateinit var subTitleMiniMode: TextView

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

        displayCard = view.findViewById(R.id.displayContainer)
        numpadPager = view.findViewById(R.id.numpadPager) as ViewPager2

        optionsButton = view.findViewById(R.id.buttonOptions)                       // Ссылка на кнопку "options"
        miniModeButton = view.findViewById(R.id.buttonMiniMode)                       // Ссылка на кнопку "options"


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

        titleMiniMode = view.findViewById(R.id.miniModeTitle)
        subTitleMiniMode = view.findViewById(R.id.miniModeSubtitle)

        titleMiniMode.alpha = 0f
        subTitleMiniMode.alpha = 0f

        // Заменяет пустоту на displayFragment
        parentFragmentManager.beginTransaction().replace(R.id.displayContainer, displayFragment).commit()

        // Иконки настроек и истории
        optionsButton.setOnClickListener { startActivity(Intent(requireContext(), OptionsActivity::class.java)) } // Option Icon
        miniModeButton.setOnClickListener { miniModeSet(miniMode); Log.i("DebugTag","miniMode: $miniMode preference: $miniModePreference"); miniMode = !miniMode } // Option Icon
    }

    override fun onResume() {
        super.onResume()
        switchModeToDeg(false)
        // После возобновлении фрагмента, получает из настроек ключи и обновляет перменные согласно ключам
        getPreferences(sharedPreferences) // Получение сохраненных настроек

        miniMode = miniModePreference

        if (miniModePreference){
            miniModeButton.visibility = View.VISIBLE // Включает кнопку минимода
            if (leftHandMode)
                miniModeButton.rotation = 180f
            else
                miniModeButton.rotation = 90f
        }
        else{
            miniModeButton.visibility = View.GONE // Выключает кнопку минимода

        }
    }

    override fun onPause() {
        super.onPause()
        // Возвращает клавиатуру в обычный режим, если включён минимод
        if (miniModePreference){
            miniModeSet(false)
            miniMode = false
        }
    }


    fun playVibration(duration: Long){
        if (vibrationPreference)
            Vibration.playVibro(requireContext(), duration)
    }


    private fun miniModeSet(reduce: Boolean){
        val duration: Long = 400
        if (reduce) {
            Animations.animatePropertyChange(titleMiniMode,"alpha",0f,0.7f, duration, Animations.overshootInterpolator)
            Animations.animatePropertyChange(subTitleMiniMode,"alpha",0f,1f, duration, Animations.overshootInterpolator)
            if (!leftHandMode){
                Animations.animatePropertyChange(numpadPager,"scaleX",1f,0.75f, duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(numpadPager,"scaleY",1f,0.75f, duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(miniModeButton,"rotation",90f,270f, duration, Animations.overshootInterpolator)



                numpadPager.pivotX = numpadPager.width.toFloat()
                numpadPager.pivotY = numpadPager.height.toFloat()
            }
            else{
                Animations.animatePropertyChange(numpadPager,"scaleX",1f,0.75f,duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(numpadPager,"scaleY",1f,0.75f,duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(miniModeButton,"rotation",180f,360f,duration, Animations.overshootInterpolator)

                numpadPager.pivotX = 0f
                numpadPager.pivotY = numpadPager.height.toFloat()
            }
        } else {
            Animations.animatePropertyChange(titleMiniMode,"alpha",0.7f,0f, duration, Animations.overshootInterpolator)
            Animations.animatePropertyChange(subTitleMiniMode,"alpha",1f,0f, duration, Animations.overshootInterpolator)
            if (!leftHandMode){
                Animations.animatePropertyChange(numpadPager,"scaleX",0.75f,1f,duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(numpadPager,"scaleY",0.75f,1f,duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(miniModeButton,"rotation",270f,90f,duration, Animations.overshootInterpolator)

                numpadPager.pivotX = numpadPager.width.toFloat()
                numpadPager.pivotY = numpadPager.height.toFloat()
            }
            else{
                Animations.animatePropertyChange(numpadPager,"scaleX",0.75f,1f,duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(numpadPager,"scaleY",0.75f,1f,duration, Animations.overshootInterpolator)
                Animations.animatePropertyChange(miniModeButton,"rotation",360f,180f,duration, Animations.overshootInterpolator)

                numpadPager.pivotX = 0f
                numpadPager.pivotY = numpadPager.height.toFloat()
            }
        }
    }

    fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        val input = displayFragment.getInputStringBuilder()

        when(input.last()){
            ConstantsCalculator.symbolCloseBracket -> {
                if (numberSymbol != ")")
                    displayFragment.enterToExpression(getString(R.string.symbolAdd))
            }

        }

        displayFragment.enterToExpression(numberSymbol)

        equalAction()
    }

    fun operationEnterAction(operator: String,ignoreAutoRechange: Boolean = false) // Обработчик нажатия - оператора (+ - * /)
    {
        val input = displayFragment.getInputStringBuilder()

        if (!ignoreAutoRechange){
            if (input.last() in setOf(ConstantsCalculator.symbolAdd,ConstantsCalculator.symbolSub,
                    ConstantsCalculator.symbolMul,ConstantsCalculator.symbolDiv,
                    ConstantsCalculator.symbolPower,ConstantsCalculator.symbolPoint))
            {
                if (input.last() != ConstantsCalculator.symbolSqrt)
                    backSpaceAction()
            }
            if (input.last() != ConstantsCalculator.symbolSqrt){
                displayFragment.enterToExpression(operator)
            }
        }
        else{
            displayFragment.enterToExpression(operator)
        }
    }

    fun sqrtAction() { // Добавляет знак квадратного корня
        operationEnterAction("$ConstantsCalculator.symbolSqrt")
        equalAction()
    }

    fun powerAction() { // Добавляет знак возведения в степень
        operationEnterAction("${ConstantsCalculator.symbolPower}")
        equalAction()
    }

    fun percentAction() { // Добавляет знак процента в поле ввода
        operationEnterAction("${ConstantsCalculator.symbolPercent}")
        equalAction()
    }

    fun factorialAction() {
        operationEnterAction("${ConstantsCalculator.symbolFactorial}")
        equalAction()
    }

    fun invertAction() {
        displayFragment.textFields[0].text = CalculatorCore.closeExpressionString(displayFragment.textFields[0].text.toString())
        equalAction()
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
        CalculatorCore.roundRange = roundRangePreference
        val newExpression = CalculatorCore.dynamicAnalyzeExpression(displayFragment.textFields[0].text.toString(),CalculatorCore.toDeg, CalculatorCore.roundRange)
        displayFragment.textFields[0].text = newExpression
        return CalculatorCore.calculateExpression(newExpression)
    }

    fun equalAction() // Событие вычислена результата
    {
        try {
            // Расчёт результата и запись в основное поле и запись результата в переменную
            displayFragment.checkTextFields()

            calculateResult = calculateAction()
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
        catch (_: Exception){
            errorHandler()
        }
    }

    fun backSpaceAction() {
        try {
            val deletedLastSymbol = displayFragment.clearLastSymbolFromExpression(displayFragment.textFields[0])

            // Если удалённый символ совпадает с одним символом из массива
            // Если удалённый символ - '.' (точка)
            if (deletedLastSymbol == '.')
                canPointEnter = true

            equalAction()
        } catch (e: Exception) {
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
        equalAction()
    }

    companion object{
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
            equalAction()
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
            equalAction()
        }
    }

    private fun errorHandler() // Обработчик ошибки
    {
        displayFragment.setResultField(resources.getString(R.string.text_failCalculate))
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
        roundRangePreference = sharedPreferences.getString(ConstantsCalculator.keysPreferences[3],"3")!!.toInt()
        vibrationPreference = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[4], false)
    }

    fun switchModeToDeg(toDeg: Boolean) {
        displayFragment.calculateForDeg(toDeg)
        CalculatorCore.toDeg = toDeg
    }

}