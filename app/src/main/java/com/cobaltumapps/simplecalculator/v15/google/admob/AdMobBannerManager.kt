package com.cobaltumapps.simplecalculator.v15.google.admob

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class AdMobBannerManager: BannerAdManager {
    private val adRequest by lazy { AdRequest.Builder().build() }
    private lateinit var adBannerView: AdView

    override fun loadAd(adView: AdView) {
        adBannerView = adView
        adBannerView.loadAd(adRequest)
    }
}