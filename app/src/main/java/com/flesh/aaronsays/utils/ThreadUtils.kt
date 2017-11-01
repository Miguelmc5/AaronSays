package com.flesh.aaronsays.utils

/**
 * Where all of the Utils for threads go
 * Created by aaronfleshner on 10/31/17.
 */

/**
 * Helper to sleep for a certain amount of time.
 */
val Long.sleep: Unit get() = Thread.sleep(this)