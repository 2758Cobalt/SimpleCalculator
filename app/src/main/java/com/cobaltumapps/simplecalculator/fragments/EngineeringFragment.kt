package com.cobaltumapps.simplecalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cobaltumapps.simplecalculator.R
import java.util.Locale

class EngineeringFragment: Fragment() {
    private var calculatorFragment: CalculatorFragment? = parentFragment as CalculatorFragment?
    private lateinit var rootContainer : androidx.gridlayout.widget.GridLayout

    private var angleTypeIsDegress: Boolean = true

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

    private lateinit var logButton: Button
    private lateinit var lnButton: Button

    private lateinit var angleTypeButton: Button

    fun setParentFragment(parent: CalculatorFragment) {
        calculatorFragment = parent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_engineering, container, false) // View фрагмента
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootContainer = view.findViewById(R.id.rootContainer)

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

        lnButton = view.findViewById(R.id.buttonLn)
        logButton = view.findViewById(R.id.buttonLog)

        angleTypeButton = view.findViewById(R.id.buttonAngleType)
        angleTypeButton.setText(if(angleTypeIsDegress) R.string.symbolDeg else R.string.symbolRad)


        sqrtButton.setOnClickListener { calculatorFragment?.sqrtAction() }
        factorialButton.setOnClickListener { calculatorFragment?.factorialAction() }
        invertButton.setOnClickListener { calculatorFragment?.invertAction() }

        // Слушатель нажатий кнопок работы с памятью
        memorySave.setOnClickListener { calculatorFragment?.memorySave() }
        memoryRead.setOnClickListener { calculatorFragment?.memoryRead() }
        memoryClear.setOnClickListener { calculatorFragment?.memoryClear() }

        // Слушатель нажатий кнопок с операциями  работы с памятью
        memoryOperationAdd.setOnClickListener { calculatorFragment?.memoryResultAdd() }
        memoryOperationSub.setOnClickListener { calculatorFragment?.memoryResultSub() }
        memoryOperationMul.setOnClickListener { calculatorFragment?.memoryResultMul() }
        memoryOperationDiv.setOnClickListener { calculatorFragment?.memoryResultDiv() }

        // Слушатель нажатий кнопок тригонометрических функций
        sinButton.setOnClickListener { calculatorFragment?.enterTrigonometryFunc(0) }
        cosButton.setOnClickListener { calculatorFragment?.enterTrigonometryFunc(1) }
        tanButton.setOnClickListener { calculatorFragment?.enterTrigonometryFunc(2) }
        ctgButton.setOnClickListener { calculatorFragment?.enterTrigonometryFunc(3) }

        piButton.setOnClickListener { calculatorFragment?.numberEnterAction(String.format(Locale.US,"%.2f",Math.PI)) }
        expButton.setOnClickListener { calculatorFragment?.numberEnterAction(String.format(Locale.US,"%.4f",Math.E)) }

        logButton.setOnClickListener { calculatorFragment?.enterTrigonometryFunc(4) }
        lnButton.setOnClickListener { calculatorFragment?.enterTrigonometryFunc(5) }

        angleTypeButton.setOnClickListener {
            calculatorFragment?.setTypeAngle(angleTypeIsDegress)                                            // Вызываем функцию, которая передаёт статус переключателя
            angleTypeIsDegress = !angleTypeIsDegress                                                        // Переключение типа угла
            angleTypeButton.setText( if(angleTypeIsDegress) R.string.symbolDeg else R.string.symbolRad )    // Назначить текст 'градусы', если true, в противном случае 'радианы'
        }
    }
}