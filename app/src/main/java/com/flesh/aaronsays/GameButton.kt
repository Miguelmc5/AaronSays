package com.flesh.aaronsays

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.util.AttributeSet
import android.widget.Button

/**
 * Created by aaronfleshner on 10/30/17.
 */
class GameButton : Button{

    constructor(context: Context):super(context){

    }
    constructor(context: Context, attrs: AttributeSet):super(context,attrs){

    }
    constructor(context: Context, attrs: AttributeSet,@AttrRes defStyleAttr :Int):super(context,attrs,defStyleAttr){

    }

    constructor(context: Context, attrs: AttributeSet, @AttrRes defStyleAttr :Int, @StyleRes defStyleRes: Int):super(context,attrs,defStyleAttr,defStyleRes){

    }

    init {
        this.setBackgroundResource(R.color.material_grey500)
    }


    //Enum Extensions
    enum class ButtonEnum{
        GREEN,RED,BLUE,YELLOW
    }

}
