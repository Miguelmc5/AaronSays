package com.flesh.aaronsays.utils

/**
 * Different time intervals for easy time manipulation.
 * Created by aaronfleshner on 10/23/17.
 */

/**
 * One second in millis
 */
val Number.SECOND: Long
    get() {
        return (this.toFloat()*1000).toLong()
    }


/**
 * One minute in millis
 */
val Number.MINUTE: Long
    get() {
        return this.SECOND*60
    }

/**
 * One hour in millis
 */
val Number.HOUR: Long
    get() {
        return this.MINUTE*60
    }

/**
 * one day in millis
 */
val Number.DAY: Long
    get() {
        return this.HOUR*24
    }