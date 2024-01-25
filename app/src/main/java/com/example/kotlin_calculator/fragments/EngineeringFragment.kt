package com.example.kotlin_calculator.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.kotlin_calculator.R

class EngineeringFragment: Fragment() {
    private var calculatorFragment: CalculatorFragment? = parentFragment as CalculatorFragment?
    private lateinit var rootContainer : androidx.gridlayout.widget.GridLayout

    private lateinit var powerButton: Button
    private lateinit var sqrtButton: Button
    private lateinit var factorialButton: Button
    private lateinit var invertButton: Button

    private lateinit var memorySave : Button
    private lateinit var memoryRead : Button
    private lateinit var memoryClear : Button

    private lateinit var memoryOperationAdd : Button
    private lateinit var memoryOperationSub : Button
    private lateinit var memoryOperationMul : Button
    private lateinit var memoryOperationDiv : Button

    private lateinit var sinButton : Button
    private lateinit var cosButton : Button
    private lateinit var tanButton : Button
    private lateinit var ctgButton : Button

    private lateinit var piButton: Button
    private lateinit var expButton: Button

    fun setParentFragment(parent: CalculatorFragment){
        calculatorFragment = parent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_engineering, container, false) // View фрагмента

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootContainer = view.findViewById(R.id.rootContainer)

        powerButton = view.findViewById(R.id.buttonPowerAction)
        sqrtButton = view.findViewById(R.id.buttonSqrtAction)
        factorialButton = view.findViewById(R.id.buttonFactorialAction)
        invertButton = view.findViewById(R.id.buttonInvert)

        memorySave = view.findViewById(R.id.buttonMemorySave)
        memoryRead = view.findViewById(R.id.buttonMemoryRead)
        memoryClear = view.findViewById(R.id.buttonMemoryClear)

        memoryOperationAdd = view.findViewById(R.id.buttonMemoryResultAdd)
        memoryOperationSub = view.findViewById(R.id.buttonMemoryResultSub)
        memoryOperationMul = view.findViewById(R.id.buttonMemoryResultMul)
        memoryOperationDiv = view.findViewById(R.id.buttonMemoryResultDiv)

        sinButton = view.findViewById(R.id.buttonSin)
        cosButton = view.findViewById(R.id.buttonCos)
        tanButton = view.findViewById(R.id.buttonTan)
        ctgButton = view.findViewById(R.id.buttonCtg)

        piButton = view.findViewById(R.id.buttonPI)
        expButton = view.findViewById(R.id.buttonExp)

        // Слушатель нажатий кнопок работы с памятью
        memorySave.setOnClickListener { calculatorFragment?.memorySave() }
        memoryRead.setOnClickListener { calculatorFragment?.memoryRead() }
        memoryClear.setOnClickListener { calculatorFragment?.memoryClear() }

        memoryOperationAdd.setOnClickListener { calculatorFragment?.memoryResultAdd() }
        memoryOperationSub.setOnClickListener { calculatorFragment?.memoryResultSub() }
        memoryOperationMul.setOnClickListener { calculatorFragment?.memoryResultMul() }
        memoryOperationDiv.setOnClickListener { calculatorFragment?.memoryResultDiv() }

        // Слушатель нажатий кнопок тригонометрических функций
        sinButton.setOnClickListener { calculatorFragment?.setConvertDisplay(0) }
        cosButton.setOnClickListener { calculatorFragment?.setConvertDisplay(1) }
        tanButton.setOnClickListener { calculatorFragment?.setConvertDisplay(2) }
        ctgButton.setOnClickListener { calculatorFragment?.setConvertDisplay(3) }

        piButton.setOnClickListener { calculatorFragment?.numberEnterAction(Math.PI.toString()) }
        expButton.setOnClickListener { calculatorFragment?.numberEnterAction(Math.E.toString()) }

        powerButton.setOnClickListener { calculatorFragment?.powerAction() }
        sqrtButton.setOnClickListener { calculatorFragment?.sqrtAction() }
        factorialButton.setOnClickListener { calculatorFragment?.factorialAction() }
        invertButton.setOnClickListener { calculatorFragment?.invertAction() }

    }

    override fun onResume() {
        super.onResume()

        if (calculatorFragment!!.expandMode){
            for (btn in arrayOf(powerButton,sqrtButton,factorialButton,invertButton)){
                btn.visibility = View.GONE
            }
        }
        else{
            for (btn in arrayOf(powerButton,sqrtButton,factorialButton,invertButton)){
                btn.visibility = View.VISIBLE
            }
        }
    }
}