package com.flesh.aaronsays

import android.app.Application
import com.google.android.gms.ads.MobileAds

/**
 * This is where the game begins
 * Created by aaronfleshner on 10/31/17.
 */
class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(applicationContext,getString(R.string.ad_mob_app_id))
    }

}