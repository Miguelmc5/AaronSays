package com.flesh.aaronsays.interfaces

import com.flesh.aaronsays.views.GameButton

/**
 * Button Interface that hightlights the button.
 * Created by aaronfleshner on 10/23/17.
 */
interface ButtonView {

    /**
     * @param color is the ButtonEnum color desc which button to highlight
     * @param isCorrect is to tell whether the user has given the correct ans. Defaults true.
     */
    fun highlightButton(color: GameButton.ButtonEnum, isCorrect: Boolean = true)

}