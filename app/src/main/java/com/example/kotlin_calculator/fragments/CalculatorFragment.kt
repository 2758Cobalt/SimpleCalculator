package com.example.kotlin_calculator.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_calculator.R
import com.example.kotlin_calculator.activities.HistoryActivity
import com.example.kotlin_calculator.activities.OptionsActivity
import com.example.kotlin_calculator.adapters.NumPadPagerAdapter
import com.example.kotlin_calculator.references.CalculatorCore
import com.example.kotlin_calculator.references.ConstantsCalculator
import com.example.kotlin_calculator.references.History
import com.example.kotlin_calculator.references.Memory
import java.util.Locale
import kotlin.math.abs

class CalculatorFragment: Fragment() {
    private lateinit var sharedPreferences: SharedPreferences                       // Объект предпочтений
    private val memory: Memory by lazy { Memory() }                                 // Объект памяти
    private val history: History by lazy { History() }                                 // Объект памяти
    private var calculateResult: Double = 0.0                                       // Результат подсчёта

    private lateinit var numpadPager: ViewPager2
    private lateinit var pagerAdapter: NumPadPagerAdapter

    // Логика
    private var canEnterOperand = true                                              // Возможность вводить операнд
    private var canEnterOperation = true                                            // Возможность вводить операцию
    private var canPowerEnter = true                                                // Вохзможность вводить знак возведения в степень
    private var canSqrtEnter = true                                                 // Возможность вводить квадратный корень
    private var canPercentEnter = true                                              // Возможность вводить процент
    private var canFactorialEnter = true                                            // Возможность вводить факториал
    private var canPointEnter = true                                                // Возможность вводить точку

    private var memoryAutoSaveResult = false                                        // Автосохранение в память

    var expandMode = false                                                          // Расширеный режим в обычном нампаде
    var leftHandMode = false                                                        // Режим левой руки

    // References
    private val displayCalculator: DisplayFragment = DisplayFragment()
    private val converterDisplay: ConverterFragment = ConverterFragment()

    private val numpadFragment: NumpadFragment = NumpadFragment()
    private val engineeringFragment: EngineeringFragment = EngineeringFragment()

    // Кнопки "иконки"
    private lateinit var optionsButton: com.google.android.material.floatingactionbutton.FloatingActionButton      // Открывает настройки
    private lateinit var historyButton: com.google.android.material.floatingactionbutton.FloatingActionButton      // Открывает историю
    private lateinit var displayCard: CardView

    private var expressionResult: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getPreferenceObject()                                   // Получение объекта
        getPreferences(sharedPreferences)                                           // Получение сохранений

        // Передаёт ссылку на себя для доступа к фрагменту
        numpadFragment.setParentFragment(this@CalculatorFragment)
        converterDisplay.setParentFragment(this@CalculatorFragment)
        engineeringFragment.setParentFragment(this@CalculatorFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false) // View фрагмента
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Настройка пейджера для клавиатуры


        numpadPager = view.findViewById(R.id.numpadPager) as ViewPager2

        optionsButton = view.findViewById(R.id.buttonOptions)                       // Ссылка на кнопку "options"
        historyButton = view.findViewById(R.id.buttonHistory)                       // Ссылка на кнопку "history"

        displayCard = view.findViewById(R.id.displayContainer)

        pagerAdapter = NumPadPagerAdapter(requireActivity(), arrayListOf(numpadFragment,engineeringFragment))
        numpadPager.adapter = pagerAdapter
        numpadPager.offscreenPageLimit = 1 // Предзагрузка  страницы пейджера

        // Анимация
        numpadPager.setPageTransformer { page, position ->
            val absPosition = abs(position)
            page.alpha = 1f - absPosition
            page.scaleY = 0.85f + (1f + absPosition) * 0.15f
            page.scaleX = 0.85f + (1f + absPosition) * 0.15f
        }

//        val bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet))
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//
//        // Настройка поведения при свайпе
//        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                // Можете выполнить дополнительные действия при изменении сдвига
//            }
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                // Обработка изменения состояния, если необходимо
//            }
//        })


        // Заменяет пустоту на displayFragment
        parentFragmentManager.beginTransaction().replace(R.id.displayContainer, displayCalculator).commit()

        // Иконки настроек и истории
        optionsButton.setOnClickListener { startActivity(Intent(requireContext(), OptionsActivity::class.java)) } // Option Icon
        historyButton.setOnClickListener { startActivity(Intent(requireContext(), HistoryActivity::class.java)) } // Option Icon

        displayCard.layoutParams.height = getScreenHeight(25f).toInt()
    }

    override fun onResume() {
        super.onResume()
        // После возобновлении фрагмента, получает из настроек ключи и обновляет перменные согласно ключам
        getPreferences(sharedPreferences)
    }

    fun setConvertDisplay(dataSetId: Int){
        converterDisplay.dataId = dataSetId
        converterDisplay.show(parentFragmentManager,converterDisplay.tag)
    }

    fun numberEnterAction(numberSymbol: String) // Обработчик нажатия - цифра (0-9) и скобки
    {
        displayCalculator.enterToExpression(numberSymbol)
        changeAccessVariableInput(canEnterOperand,
            operationEnter = true,
            powerEnter = true,
            sqrtEnter = true,
            factorialEnter = true)
        canPercentEnter = true

        equalAction()
    }

    fun operationEnterAction(operator: String) // Обработчик нажатия - оператора (+ - * /)
    {
        if (canEnterOperation){
            displayCalculator.enterToExpression(operator)

            changeAccessVariableInput(canEnterOperand,
                operationEnter = true,
                powerEnter = canPowerEnter,
                sqrtEnter = canSqrtEnter,
                factorialEnter = canFactorialEnter)
            canPointEnter = true
        }
    }

    fun sqrtAction() { // Добавляет знак квадратного корня
        canEnterOperation = true
        operationEnterAction("√")
        equalAction()
    }

    fun powerAction() { // Добавляет знак возведения в степень
        operationEnterAction("^")
        equalAction()
    }

    fun percentAction() { // Добавляет знак процента в поле ввода
        canEnterOperation = true
        operationEnterAction("%")
        equalAction()
    }

    fun factorialAction() {
        canEnterOperation = true
        operationEnterAction("!")
        equalAction()
    }

    fun invertAction() {
        canEnterOperation = true
        displayCalculator.textFields[0].text = "1/" + CalculatorCore.closeExpressionString(displayCalculator.textFields[0].text.toString())
        equalAction()
    }

    fun enterPointAction() // Ввод точки
    {
        // После ввода - отключает возможность повторно ввести
        if (canPointEnter)
            displayCalculator.enterToExpression(ConstantsCalculator.operatorConstants[5])
        canPointEnter = false
    }

    // Расчитывает результат и возвращает его
    private fun calculateAction(): Double{
        val newExpression = CalculatorCore.dynamicAnalyzeExpression(displayCalculator.textFields[0].text.toString())
        displayCalculator.textFields[0].text = newExpression
        return CalculatorCore.calculateExpression(newExpression)
    }

    fun backSpaceAction() {
        try {
            val deletedLastSymbol = displayCalculator.clearLastSymbolFromExpression(displayCalculator.textFields[0])

            // Если удалённый символ совпадает с одним символом из массива
            if (ConstantsCalculator.operandConstants.contains(deletedLastSymbol.toString())){
                canEnterOperation = true
            }
            // Если удалённый символ - '.' (точка)
            if (deletedLastSymbol == '.')
                canPointEnter = true

            equalAction()
        } catch (e: Exception) {
            errorHandler()
        }
    }

    fun equalAction() // Событие вычислена результата
    {
        try {
            // Расчёт результата и запись в основное поле и запись результата в переменную
            displayCalculator.checkTextFields()

            calculateResult = calculateAction()
            val result = StringBuilder(calculateResult.toString())

            if (result[result.lastIndex - 1] == '.' && result[result.lastIndex] == '0'){ // Если в ответе последний символ после точки = 0 - значит число является типом Int
                displayCalculator.setResultField(calculateResult.toInt().toString()) // Вывод в Int
            }
            else{
                displayCalculator.setResultField(calculateResult.toString()) // Вывод в Double

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

    fun roundAction(roundRange: Int = 1) // Округление результата до десятых
    {
        val roundedResult = displayCalculator.textFields[1].text.toString().toDouble()
        displayCalculator.textFields[1].text = String.format(Locale.US, "%.${roundRange}f", roundedResult)
    }

    fun clearAllAction() // Обработчик нажатия - очистка полей
    {
        displayCalculator.clearTextField(
            inputField = true,
            resultField = true,
            memoryField = false)

        changeAccessVariableInput(true,
            operationEnter = true,
            powerEnter = true,
            sqrtEnter = true,
            factorialEnter = true)
        canPercentEnter = true
    }

    fun clearGroupAction() {

        displayCalculator.textFields[0].text = removeDigitsFromEnd(displayCalculator.textFields[0].text.toString())
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

    private fun changeAccessVariableInput(numberOperand: Boolean = true, operationEnter: Boolean = false,
                                          powerEnter: Boolean = false, sqrtEnter: Boolean = false, factorialEnter: Boolean = false){
        canEnterOperand = numberOperand
        canEnterOperation = operationEnter
        canPowerEnter = powerEnter
        canSqrtEnter = sqrtEnter
        canFactorialEnter = factorialEnter
    }

    // Работа с памятью
    fun memorySave(){
        if (expressionResult.isNotEmpty()){
            val savedMemoryValue = memory.save(expressionResult.toDouble())
            displayCalculator.setMemoryField(memory.read().toString())
        }
    }

    fun memoryRead(){
        if (expressionResult.isNotEmpty()){
            val readMemory = memory.read()
            operationEnterAction("*")
            displayCalculator.enterToExpression(readMemory.toString())
            equalAction()
        }
    }

    fun memoryClear() {
        memory.clear()
        displayCalculator.clearTextField(inputField = false, resultField = false, memoryField = true)
    }

    fun memoryResultAdd(){
        memory.addToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayCalculator.setMemoryField(readMemory.toString())
    }

    fun memoryResultSub(){
        memory.subToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayCalculator.setMemoryField(readMemory.toString())
    }
    fun memoryResultMul(){
        memory.mulToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayCalculator.setMemoryField(readMemory.toString())
    }
    fun memoryResultDiv(){
        memory.divToResult(expressionResult.toDouble()) // Добавляет новую запись к старой записи
        val readMemory = memory.read()
        displayCalculator.setMemoryField(readMemory.toString())
    }

    fun saveExpressionToHistory(){
        if(expressionResult.isNotEmpty()){
            history.saveToHistory(calculateResult.toString(),expressionResult)

            Log.i("DebugTag","История:")
            
        }
    }

    fun pushExpressionToLast(){
        if(expressionResult.isNotEmpty()){
            displayCalculator.enterToLastExpression()
            displayCalculator.setInputField(displayCalculator.textFields[1].text.toString())
            equalAction()
        }
    }

    private fun errorHandler() // Обработчик ошибки
    {
        displayCalculator.textFields[1].text = "..."
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
        expandMode = sharedPreferences.getBoolean(ConstantsCalculator.keysPreferences[2], false)
    }

    private fun getScreenHeight(percentScreen: Float): Float {
        val heightPixels = resources.displayMetrics.heightPixels
        return heightPixels * (percentScreen / 100f)
    }

}