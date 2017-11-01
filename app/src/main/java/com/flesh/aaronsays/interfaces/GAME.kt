package com.flesh.aaronsays.interfaces

/**
 * Game interface this is all of the parts of the game.
 * Created by aaronfleshner on 10/30/17.
 */
interface GAME {

    /**
     * String the Simon Says game
     */
    fun startGame()

    /**
     *  Disable all user interactions with game
     */
    fun disableUserInteraction()

    /**
     * Enable all user interaction with game
     */
    fun enableUserInteraction()

    /**
     * Users move was correct
     */
    fun correctMove()

    /**
     * User move was incorrect
     */
    fun incorrectMove()

    /**
     * End the current game being played
     */
    fun endGame()

    /**
     * Restart the a new game by resting all vars and starting game again.ß
     */
    fun restartGame()

}