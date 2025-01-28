package com.cobaltumapps.simplecalculator.v15.google.billing

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeaturesViewModel(
    private val billingClientManager: BillingClientManager,
    private val billingConnectionManager: BillingConnectionManager
) : ViewModel() {

    private val _productDetails = MutableStateFlow<List<ProductDetails>>(emptyList())
    val productDetails: StateFlow<List<ProductDetails>> get() = _productDetails

    init {
        connectToBillingService()
    }

    private fun connectToBillingService() {
        viewModelScope.launch {
            billingConnectionManager.connect {
                // Когда подключение успешно, загружаем данные о продуктах
                billingClientManager.queryProductDetails { productDetailsList ->
                    _productDetails.value = productDetailsList
                }
            }
        }
    }

    fun purchaseProduct(activity: Activity, productDetails: ProductDetails) {
        billingClientManager.launchPurchaseFlow(activity, productDetails)
    }
}
