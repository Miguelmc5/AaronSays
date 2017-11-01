package com.flesh.aaronsays.utils

import android.content.Context

/**
 * Shared Prefs helper class for Score Keeping.
 * Created by aaronfleshner on 10/30/17.
 */
class ScoreUtils(context: Context) {

    val KEY = "[${ScoreUtils::class.java.canonicalName}] HIGH_SCORE"
    val prefs = GamePreferences(context)


    fun setHighScore(highScore: Int){
        prefs.set(KEY,highScore)
    }

    fun getHighScore() = prefs.get(KEY,0)

}