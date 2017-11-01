package com.flesh.aaronsays.interfaces

/**
 * Game interface this is all of the parts of the game.
 * Created by aaronfleshner on 10/30/17.
 */
interface GAME {

    fun startGame()
    fun disableUserInteraction()
    fun enableUserInteraction()
    fun correctMove()
    fun incorrectMove()
    fun endGame()
    fun restartGame()

}