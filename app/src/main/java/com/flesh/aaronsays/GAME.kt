package com.flesh.aaronsays

/**
 * Game interface
 * Created by aaronfleshner on 10/30/17.
 */
interface GAME {

    fun startGame()
    fun disableUserInteraction()
    fun enableUserInteraction()
    fun correctMove()
    fun incorrectMove()
    fun endGame()

}