package com.cobaltumapps.simplecalculator.fragments.calculator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.activities.ConverterActivity
import com.cobaltumapps.simplecalculator.activities.OptionsActivity
import com.cobaltumapps.simplecalculator.adapters.viewpagers.NumpadPagerAdapter
import com.cobaltumapps.simplecalculator.dialogs.UpdateBoardDialogFragment
import com.cobaltumapps.simplecalculator.managers.MemoryManager
import com.cobaltumapps.simplecalculator.references.Animations
import com.cobaltumapps.simplecalculator.references.ConstantsCalculator
import com.cobaltumapps.simplecalculator.references.IntentKeys
import com.cobaltumapps.simplecalculator.references.LogTags
import com.cobaltumapps.simplecalculator.references.PreferenceKeys
import com.cobaltumapps.simplecalculator.references.SharedKeys
import com.cobaltumapps.simplecalculator.services.VibratorService
import com.cobaltumapps.simplecalculator.system.CalculatorOld
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.min

private const val KEYSTORE_UPDATE_BOARD_STATE_SHOW = "updateBoardState_key"
private const val LOG_TAG_CALCULATOR = "DebugCalculator"

private const val UPDATEBOARD_FORCELAUCNH = false // Принудительно показывает окно "Что нового"


class CalculatorFragment: Fragment() {
    private var calculateResult: Double = 0.0                                       // Результат подсчёта
    lateinit var numpadPager: ViewPager2
    private lateinit var pagerAdapter: NumpadPagerAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    // Логика
    private var canPointEnter = true                                                // Возможность вводить точку
    private var memoryAutoSaveResult = false                                        // Автосохранение в память
    var leftHandMode: Boolean = false                                               // Режим левой руки
    private var miniMode: Boolean = false                                           // Состояние мини-клавиатуры (true/false)
    private var miniModePreference: Boolean = false
    private var vibrationPreference: Boolean = false
    private var saveLastCalculationPreference: Boolean = false

    // References
    private val vibrator = VibratorService()
    private val calculatorOldSystem: CalculatorOld = CalculatorOld() // Объект калькулятора
    private val displayFragment: DisplayFragment = DisplayFragment()                // Фрагмент дисплея калькулятора
    private val numpadFragment: NumpadFragment = NumpadFragment()                   // Фрагмент обычной клавиатуры
    private val engineeringFragment: EngineeringFragment = EngineeringFragment()    // Фрагмент научной клавиатуры
    private val memory: MemoryManager by lazy { MemoryManager() }                                 // Объект памяти

    private val converterActivity = ConverterActivity() // Активити конвертора

    private lateinit var sharedPreferences: SharedPreferences                       // Хранилище

    // Кнопки "иконки"
    private lateinit var optionsButton: ImageView     // Открывает настройки
    private lateinit var miniModeButton: ImageView     // Включает мини-режим
    private lateinit var convertersButton: ImageView     // Иконка бекспейса
    private lateinit var backspaceIconButton: ImageView     // Иконка бекспейса

    private lateinit var clearAllFullscreenButton: ImageView     // Иконка полной очистки в полноэкранном режиме
    private lateinit var settingsFullscreenButton: ImageView     // Иконка настроек в полноэкранном режиме

    private lateinit var displayCard: FrameLayout

    private lateinit var titleMiniMode: TextView

    private var expressionResult: String = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getPreferenceObject()                                   // Получение объекта

        // Передаёт ссылку на себя для доступа к фрагменту
        displayFragment.setParentFragment(this@CalculatorFragment)
        numpadFragment.setParentFragment(this@CalculatorFragment)
        engineeringFragment.setParentFragment(this@CalculatorFragment)

        vibrator.context = context
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val resultValue = data?.getDoubleExtra(IntentKeys.memoryKey,0.0)
                Log.i(LogTags.LOG_CONVERTER,resultValue.toString())
            }
        }
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

        clearAllFullscreenButton = view.findViewById(R.id.buttonClearAllFullscreen)
        settingsFullscreenButton = view.findViewById(R.id.buttonSettingsFullscreen)

        backspaceIconButton.alpha = 0f

        pagerAdapter = NumpadPagerAdapter(requireActivity(), arrayListOf(numpadFragment, engineeringFragment))
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
        optionsButton.setOnClickListener { startActivity(Intent(requireContext(), OptionsActivity::class.java)) }

        miniModeButton.setOnClickListener { miniModeSet(miniMode); miniMode = !miniMode
            Animations.playAnimation(
                requireActivity(),
                miniModeButton,
                R.anim.scale_out,
                Animations.overshootInterpolator
            )
        }

        backspaceIconButton.setOnClickListener { backSpaceAction(); playVibration(5) }
        backspaceIconButton.setOnLongClickListener { clearAllAction(); playVibration(20); true }

        clearAllFullscreenButton.setOnClickListener { clearAllAction(); displayFragment.clearLastExpression(); playVibration(20); }
        settingsFullscreenButton.setOnClickListener { optionsButton.callOnClick() }

        convertersButton.setOnClickListener {
            val intent = Intent(requireActivity(), converterActivity::class.java)
            intent.putExtra(IntentKeys.memoryKey, memory.read().toDouble())
            intent.putExtra(IntentKeys.vibrationBlockKey, !vibrationPreference)

            resultLauncher.launch(intent)
        }

        val versionCode = context?.packageManager!!.getPackageInfo(context?.packageName!!,0).versionCode

        if (UPDATEBOARD_FORCELAUCNH){
            UpdateBoardDialogFragment().show(parentFragmentManager, UpdateBoardDialogFragment.TAG)
        }
        else{
            if (sharedPreferences.getInt(KEYSTORE_UPDATE_BOARD_STATE_SHOW,0) != versionCode){
                UpdateBoardDialogFragment().show(parentFragmentManager,
                    UpdateBoardDialogFragment.TAG)

                val editor = sharedPreferences.edit()
                editor.putInt(KEYSTORE_UPDATE_BOARD_STATE_SHOW,versionCode)
                editor.apply()
            }
        }

    }

    override fun onPause() {
        super.onPause()

        // Возвращает клавиатуру в обычный режим, если включён минимод
        if (miniModePreference) {
            miniMode = false
            miniModeSet(false)
        }

        // Сохранения значений в дисплее при остановке фрагмента
        saveData()
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

        // Загрузка значений в дисплей при возобновлении фрагмента
        loadData()


        updateMemoryField()
    }


    fun playVibration(duration: Long){
        vibrator.isBlocked = !vibrationPreference

        vibrator.playVibration(duration)
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

        miniModeButton.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                if (reduce) R.drawable.ic_numpad_expand else R.drawable.ic_numpad_reduce,
                resources.newTheme()
            )
        ) // Смена иконки мини-режима

        Animations.animatePropertyChange(
            titleMiniMode,
            "alpha",
            startAlpha,
            endAlpha,
            duration,
            Animations.overshootInterpolator
        )
        numpadPager.pivotX = pivotX
        numpadPager.pivotY = pivotY
        viewScaling(numpadPager, startScale, endScale, duration)
    }


    private fun viewScaling(view: View, scaleFrom: Float = 0.75f, scaleTo: Float = 1f, duration: Long = 300){
        Animations.animatePropertyChange(
            view,
            "scaleX",
            scaleFrom,
            scaleTo,
            duration,
            Animations.overshootInterpolator
        )
        Animations.animatePropertyChange(
            view,
            "scaleY",
            scaleFrom,
            scaleTo,
            duration,
            Animations.overshootInterpolator
        )

    }

    fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        if (displayFragment.getInputField().last() == ConstantsCalculator.symbolFactorial){
            displayFragment.enterToExpression(ConstantsCalculator.symbolMul.toString())
        }
        displayFragment.enterToExpression(numberSymbol)
        playVibration(5)
        calculateExpression()
    }

    fun operationEnterAction(operator: String,ignoreAutoRechange: Boolean = false) // Обработчик нажатия - оператора (+ - * /)
    {
        val input = displayFragment.getInputStringBuilder()
        val lastSymbol = input.last()


        if (!ignoreAutoRechange){

            if (lastSymbol in setOf(
                    ConstantsCalculator.symbolAdd, ConstantsCalculator.symbolSub,
                    ConstantsCalculator.symbolMul, ConstantsCalculator.symbolDiv,
                    ConstantsCalculator.symbolPower, ConstantsCalculator.symbolPoint
                ))
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
        playVibration(10)
        calculateExpression()
    }

    fun sqrtAction() { // Добавляет знак квадратного корня
        operationEnterAction("${ConstantsCalculator.symbolSqrt}")
    }

    fun powerAction() { // Добавляет знак возведения в степень
        operationEnterAction("${ConstantsCalculator.symbolPower}")
    }
    fun equalAction(){
        calculateExpression()
    }

    fun percentAction() { // Добавляет знак процента в поле ввода
        operationEnterAction("${ConstantsCalculator.symbolPercent}")
    }

    fun factorialAction() {
        // Вставляем символ факториала в строку выражения
        operationEnterAction("${ConstantsCalculator.symbolFactorial}")

    }

    fun invertAction() {
        displayFragment.setInputField( calculatorOldSystem.closeExpressionString(displayFragment.getInputField() ))
        calculateExpression()
    }

    fun enterTrigonometryFunc(dataSetId: Int){
        if (displayFragment.textFields[0].text.last() !in setOf(
                ConstantsCalculator.symbolAdd, ConstantsCalculator.symbolSub,
                ConstantsCalculator.symbolMul, ConstantsCalculator.symbolDiv,
                ConstantsCalculator.symbolPower
            ))
        {
            if (displayFragment.textFields[0].text.first() != '0')
                displayFragment.enterToExpression(getString(R.string.symbolMul)) // Ставит умножение, если нет других операторов
            else{
                displayFragment.textFields[0].text = "1"
                displayFragment.enterToExpression(getString(R.string.symbolMul)) // Ставит умножение, если нет других операторов
            }
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
            playVibration(5)
        }
    }

    // Расчитывает результат и возвращает его
    private fun calculateAction(): Double{
        // Получение введённого выражения
        val expression = displayFragment.getInputField()

        // Объявление калькулятора и передача выражения
        val calculatorOld = CalculatorOld(expression)

        // Вызов метода "calculate" и расчёт результата
        calculatorOld.calculate(true)

        return calculatorOld.getResult()
    }

    fun calculateExpression() // Событие вычислена результата
    {
        try {
            // Расчёт результата и запись в основное поле и запись результата в переменную
            displayFragment.checkTextFields()

            calculateResult = calculateAction()

            // Если результат вычислений - бесконечность
            if (calculateResult.isInfinite()){
                displayFragment.setResultField(resources.getString(R.string.error_infinite))
            }

            // Если результат вычислений - не число
            if (calculateResult.isNaN()){
                displayFragment.setResultField(resources.getString(R.string.error_nan))
            }

            else{
                // Результат
                val result = StringBuilder(calculateResult.toString())

                if (result[result.lastIndex - 1] == '.' && result[result.lastIndex] == '0'){ // Если в ответе последний символ после точки = 0 - значит число является типом Int
                    val builder = StringBuilder(result)
                        .deleteAt(result.lastIndex)
                        .deleteAt(result.lastIndex - 1)
                    displayFragment.setResultField(builder.toString()) // Вывод в Int
                }
                else{
                    displayFragment.setResultField(result.toString().replace(',', '.')) // Вывод в Double с заменой запятой на точку

                }
                expressionResult = calculateResult.toString()

                // Если автосохранение включено
                if(memoryAutoSaveResult){
                    memorySave()
                }
            }

        }
        catch (ex: Exception){
            Log.e(LogTags.LOG_EXCEPTIONS, ex.toString())
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
        playVibration(5)
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

            if (expressionResult.isDouble())
                memory.save(expressionResult.toDouble())
            if (expressionResult.isInt())
                memory.save(expressionResult.toInt())
            Log.i(LOG_TAG_CALCULATOR,expressionResult)

            updateMemoryField()
        }
    }

    fun memoryRead(){
        if (expressionResult.isNotEmpty()){
            val readMemory = memory.read()
            operationEnterAction(getString(R.string.symbolMul))
            displayFragment.enterToExpression(readMemory)
            calculateExpression()
        }
    }

    fun memoryClear() {
        memory.clear()
        displayFragment.clearTextField(inputField = false, resultField = false, memoryField = true)
    }

    fun memoryResultAdd(){
        memory.addToResult(expressionResult.toDouble()); Log.i("DebugTag","yes") // Добавляет новую запись к старой записи
        updateMemoryField()
    }

    fun memoryResultSub(){
        if (expressionResult.isDouble() || expressionResult.isInt())
            memory.subToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        updateMemoryField()
    }
    fun memoryResultMul(){
        if (expressionResult.isDouble() || expressionResult.isInt())
            memory.mulToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        updateMemoryField()
    }
    fun memoryResultDiv(){
        if (expressionResult.isDouble() || expressionResult.isInt())
            memory.divToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        updateMemoryField()
    }

    private fun updateMemoryField(){
        displayFragment.setMemoryField(memory.read())
    }
    fun pushExpressionToLast() {
        if (expressionResult.isNotEmpty()) {
            displayFragment.enterToLastExpression()

            // Преобразовываем результат из научного формата в полное число
            val resultWithoutScientificNotation = try {
                BigDecimal(displayFragment.getResultField().replace(" ", "")).toPlainString()
            } catch (e: NumberFormatException) {
                // Если преобразование не удаётся, используем исходную строку
                displayFragment.getResultField()
            }

            displayFragment.setInputField(resultWithoutScientificNotation)
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
        memoryAutoSaveResult = sharedPreferences.getBoolean(PreferenceKeys.keyMemoryAutoSave, false)
        saveLastCalculationPreference = sharedPreferences.getBoolean(PreferenceKeys.keySaveLastCalculation, true)

        leftHandMode = sharedPreferences.getBoolean(PreferenceKeys.keyLeftHandMode, false)
        miniModePreference = sharedPreferences.getBoolean(PreferenceKeys.keyOneHandedMode, false)
        vibrationPreference = sharedPreferences.getBoolean(PreferenceKeys.keyAllowVibration, true)
    }

    fun setTypeAngle(isDegree: Boolean) {
        displayFragment.updateAngleType(isDegree)
        calculatorOldSystem.setRadiansState(isDegree)
        calculateExpression()
    }

    private fun loadData(){
        if (saveLastCalculationPreference){
            displayFragment.setLastField(sharedPreferences.getString(SharedKeys.lastExpressionKey,"0")!!)
            displayFragment.setInputField(sharedPreferences.getString(SharedKeys.inputExpressionKey,"0")!!)
            displayFragment.setResultField(sharedPreferences.getString(SharedKeys.resultExpressionKey,"0")!!)
        }
    }

    fun saveData(){
        if (saveLastCalculationPreference){
            val editor = sharedPreferences.edit()

            editor.putString(SharedKeys.lastExpressionKey,displayFragment.getLastField())
            editor.putString(SharedKeys.inputExpressionKey,displayFragment.getInputField())
            editor.putString(SharedKeys.resultExpressionKey,displayFragment.getResultField())

            editor.apply()

        }
    }

    fun checkAndConvert(value: String): Number? {
        return when {
            value.isDouble() -> value.toDouble()
            value.isInt() -> value.toInt()
            else -> null
        }
    }

    fun String.isDouble(): Boolean {
        return this.toDoubleOrNull() != null
    }

    fun String.isInfinite(): Boolean {
        return (this.isDouble()) &&
        (this.toDouble() != Double.POSITIVE_INFINITY || this.toDouble() != Double.NEGATIVE_INFINITY)
    }

    fun String.isInt(): Boolean {
        return this.toIntOrNull() != null
    }
}