package com.flesh.aaronsays

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimeUnitTests {

    @Test
    fun half_a_second_in_millis(){
        val millis = 500L
        assertEquals(.5.SECOND,millis)
    }

    @Test
    fun one_second_in_millis(){
        val millis = 1000L
        assertEquals(1.SECOND,millis)
    }
    @Test
    fun one_minute_in_millis(){
        val millis = 60000L
        assertEquals(1.MINUTE,millis)
    }
    @Test
    fun one_hour_in_millis(){
        val millis = 3600000L
        assertEquals(1.HOUR,millis)
    }
    @Test
    fun one_day_in_millis(){
        val millis = 86400000L
        assertEquals(1.DAY,millis)
    }
}
