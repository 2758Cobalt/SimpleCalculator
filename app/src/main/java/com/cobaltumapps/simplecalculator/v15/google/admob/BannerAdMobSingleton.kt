package com.cobaltumapps.simplecalculator.v15.google.admob

object BannerAdMobSingleton {
    @Volatile
    private var instance: AdMobBannerManager? = null

    fun getInstance(): AdMobBannerManager {
        return instance ?: synchronized(this) {
            instance ?: AdMobBannerManager().also {
                instance = it
            }
        }
    }
}