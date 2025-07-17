package com.cobaltumapps.simplecalculator.ui.components.extra

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import com.cobaltumapps.simplecalculator.databinding.ViewExtraInputFieldBinding
import com.google.android.material.textfield.TextInputLayout

class ExtraInputFieldView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet, defAttrSet: Int = 0
): TextInputLayout(context, attrs, defAttrSet) {

    private val binding: ViewExtraInputFieldBinding =
        ViewExtraInputFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {

//        context.theme.obtainStyledAttributes(attrs, R.styleable, 0, 0).apply {
//            try {
//            } finally {
//                recycle()
//            }
//        }

    }
    fun setHintText(hint: String) { binding.viewExtraInputField.setHint(hint) }
    fun setHelperText(helperText: String) { binding.viewExtraInputContainer.helperText = helperText }
    fun setText(text: String) { binding.viewExtraInputField.setText(text) }

    fun setTextListener(textWatcher: TextWatcher) {
        binding.viewExtraInputField.addTextChangedListener(textWatcher)
    }
}