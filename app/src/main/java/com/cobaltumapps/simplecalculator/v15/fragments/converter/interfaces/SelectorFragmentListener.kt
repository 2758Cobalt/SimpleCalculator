package com.cobaltumapps.simplecalculator.v15.fragments.converter.interfaces

import com.cobaltumapps.simplecalculator.v15.fragments.converter.enums.ConverterUnit

interface SelectorFragmentListener {
    /** Method, works when the item has been selected
     * @param selectedUnit Selected item */
    fun onSelectedItem(selectedUnit: ConverterUnit)
}