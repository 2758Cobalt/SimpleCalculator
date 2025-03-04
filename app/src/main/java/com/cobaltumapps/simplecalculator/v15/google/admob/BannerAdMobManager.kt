package com.cobaltumapps.simplecalculator.v15.google.admob

import androidx.core.view.isVisible
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class BannerAdMobManager: BannerAdManager, BannerAdStates {
    private val adRequest by lazy { AdRequest.Builder().build() }
    private lateinit var adBannerView: AdView

    override fun loadAd(adView: AdView) {
        adBannerView = adView
        adBannerView.loadAd(adRequest)
    }

    override fun hideAd() {
        adBannerView.isVisible = false
    }

    override fun showAd() {
        adBannerView.isVisible = true
    }

    override fun pauseAd() {
        adBannerView.pause()
    }

    override fun resumeAd() {
        adBannerView.resume()
    }

    override fun destroyAd() {
        adBannerView.destroy()
    }
}