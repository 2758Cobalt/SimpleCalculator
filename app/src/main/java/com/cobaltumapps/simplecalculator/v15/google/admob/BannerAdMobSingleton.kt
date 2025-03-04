package com.cobaltumapps.simplecalculator.v15.google.admob

import com.google.android.gms.ads.AdView

object BannerAdMobSingleton: BannerAdManager, BannerAdStates {
    @Volatile
    private var instance: BannerAdMobManager? = null

    fun getInstance(): BannerAdMobManager {
        return instance ?: synchronized(this) {
            instance ?: BannerAdMobManager().also {
                instance = it
            }
        }
    }

    override fun loadAd(adView: AdView) {
        instance?.loadAd(adView)
    }

    override fun hideAd() {
        instance?.hideAd()
    }

    override fun showAd() {
        instance?.showAd()
    }

    override fun pauseAd() {
        instance?.pauseAd()
    }

    override fun resumeAd() {
        instance?.resumeAd()
    }

    override fun destroyAd() {
        instance?.destroyAd()
    }
}