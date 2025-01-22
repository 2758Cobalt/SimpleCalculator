package com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayAngleViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayExpressionViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayMemoryViewer
import com.cobaltumapps.simplecalculator.v15.calculator.components.display.interfaces.viewers.DisplayViewerCleaner

interface DisplayExpressionControl:
    DisplayExpressionViewer,
    DisplayViewerCleaner,
    DisplayMemoryViewer,
    DisplayAngleViewer