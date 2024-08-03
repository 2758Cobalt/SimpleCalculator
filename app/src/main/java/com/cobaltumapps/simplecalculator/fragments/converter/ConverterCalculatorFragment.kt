package com.cobaltumapps.simplecalculator.fragments.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cobaltumapps.simplecalculator.R
import com.cobaltumapps.simplecalculator.interfaces.AdvancedNumpadListener
import com.cobaltumapps.simplecalculator.managers.MemoryManager
import com.cobaltumapps.simplecalculator.references.IntentKeys
import com.cobaltumapps.simplecalculator.references.LogTags

class ConverterCalculatorFragment: Fragment(), AdvancedNumpadListener {
    private var memory = MemoryManager()

    // Ссылка на конвертер
    lateinit var unitConverterReference: UnitConverterFragment

    // Ссылка на viewpager
    lateinit var parentViewPager: ViewPager2

    private lateinit var numpadUserInputField: TextView
    private lateinit var memoryTextField: TextView

    private lateinit var numpadFrame: FrameLayout

    private val numpadFragment: ConverterNumpadFragment = ConverterNumpadFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_converter_calculator, container, false)

        numpadFrame = view.findViewById(R.id.converterNumpadFrame)

        numpadUserInputField = view.findViewById(R.id.converterNumpadUserInput)

        memoryTextField = view.findViewById(R.id.memoryField)


        childFragmentManager.beginTransaction().replace(R.id.converterNumpadFrame, numpadFragment).commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numpadFragment.setNumpadListener(this)
        updateMemoryDisplay()
        numpadFragment.vibrator.isBlocked = requireActivity().intent.getBooleanExtra(IntentKeys.vibrationBlockKey, false)

    }

    fun setMemoryManager(newMemory: MemoryManager){
        this.memory = newMemory
    }

    fun getMemoryManager(): MemoryManager{
        return this.memory
    }

    // Получаем пользовательский ввод
    fun getUserInput(): Double {
        return numpadUserInputField.text.toString().toDouble()
    }

    fun setUserInput(newInput: String){
        numpadUserInputField.text = newInput
    }

    override fun onClickMemorySave() {
        memory.save(getUserInput())
        updateMemoryDisplay()
    }

    override fun onClickMemoryRead() {
        setUserInput(memory.read())
    }

    override fun onClickMemoryClear() {
        memory.clear()
        updateMemoryDisplay()
    }

    // Обновляет поле, отображаемое значение в памяти
    private fun updateMemoryDisplay(){
        memoryTextField.text = memory.read()
        Log.i(LogTags.LOG_CONVERTER,memory.read())
    }

    // При нажатии на цифру
    override fun onClickNumberButton(number: String) {
        if (numpadUserInputField.text.toString() == "0")
            setUserInput(number)
        else
            numpadUserInputField.append(number)

    }

    // При нажатии на операцию
    override fun onClickPointButton() {
        if (!numpadUserInputField.text.toString().contains('.')){
            numpadUserInputField.append(".")
        }
    }

    override fun onClickAllClearButton() {
        setUserInput("0")
    }

    override fun onClickBackSpaceButton() {
        if (numpadUserInputField.text!!.isNotEmpty()){
            val currentText = numpadUserInputField.text.toString()
            val newText = currentText.substring(0, currentText.length - 1)

            setUserInput(newText)

            // Проверяем пусто ли поле ввода
            if (numpadUserInputField.text!!.isEmpty()){

                setUserInput("0")
            }
        }
    }

    override fun onClickEnterButton() {
        parentViewPager.setCurrentItem(0,true)
        unitConverterReference.setUserInput(getUserInput())
        unitConverterReference.calculate()
    }

    override fun onDestroy() {
        super.onDestroy()
        numpadFragment.removeNumpadListener()
    }

}