package com.flesh.aaronsays.interfaces

import com.flesh.aaronsays.views.GameButton

/**
 * Button Interface that hightlights the button.
 * Created by aaronfleshner on 10/23/17.
 */
interface ButtonView {

    fun highlightButton(color: GameButton.ButtonEnum, isCorrect: Boolean = true)

}