package com.flesh.aaronsays.utils

import android.content.Context

/**
 * The helper class that keeps track of all of the games played
 * Created by aaronfleshner on 10/31/17.
 */
class GamesPlayedUtil(context: Context) {

    val KEY = "[${GamesPlayedUtil::class.java.canonicalName}] TOTAL GAMES"
    val FIRST_TIMER_KEY = "[${GamesPlayedUtil::class.java.canonicalName}] FIRST TIMER"

    val prefs = GamePreferences(context)
    val threshold = 5

    /**
     * Player is a first time player
     * @return true if user is within threshold (5)
     */
    fun isFirstTimer():Boolean{
        return prefs.get(KEY,0L)<=threshold
    }

    /**
     * Adds a game to tolal games played and first time games played.
     */
    fun anotherGamePlayed(){
        var gamesPlayed = prefs.get(KEY,0L)
        var firstTimerGames = prefs.get(FIRST_TIMER_KEY,0L)
        gamesPlayed++//add 1 to games played.
        firstTimerGames++//add 1 to games played.
        prefs.set(KEY,gamesPlayed)
        prefs.set(FIRST_TIMER_KEY,firstTimerGames)
    }


    /**
     * Resets the first timer games played for when player has been away a while or because I say so.
     */
    fun resetFirstTimerGames(){
        prefs.remove(FIRST_TIMER_KEY)
    }


}