package com.flesh.aaronsays

import android.support.annotation.IdRes

/**
 * Created by aaronfleshner on 10/23/17.
 */
interface ButtonView {
    fun highlightRedButton(isCorrect:Boolean = true)
    fun highlightGreenButton(isCorrect:Boolean = true)
    fun highlightBlueButton(isCorrect:Boolean = true)
    fun highlightYellowButton(isCorrect:Boolean = true)
}