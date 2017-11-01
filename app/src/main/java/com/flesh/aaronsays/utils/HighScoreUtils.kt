package com.flesh.aaronsays.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Shared Prefs helper class for Score Keeping.
 * Created by aaronfleshner on 10/30/17.
 */
class HighScoreUtils(context: Context) {

    val KEY = "[${HighScoreUtils::class.java.canonicalName}] HIGH_SCORE"

    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val edit = prefs.edit()


    fun setHighScore(highScore: Int){
        edit.putInt(KEY,highScore).apply()
    }

    fun getHighScore() = prefs.getInt(KEY,0)

}