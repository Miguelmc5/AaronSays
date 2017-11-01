package com.flesh.aaronsays.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * Common Shared Prefs throughout app
 * Created by aaronfleshner on 10/31/17.
 */
class GamePreferences(context: Context) {

    private val prefs = context.getSharedPreferences(GamePreferences::class.java.simpleName,MODE_PRIVATE)
    private val edit = prefs.edit()

    @SuppressWarnings("unchecked")
    fun set(key:String, any : Any){
        when(any){
            is Long -> edit.putLong(key, any)
            is Boolean -> edit.putBoolean(key, any)
            is Float->edit.putFloat(key, any)
            is String->edit.putString(key, any)
            is Set<*>->{
                //check if set is strings
                if(any.iterator().next() is String) {
                    edit.putStringSet(key, any as Set<String>)
                }
            }
            is Int->edit.putInt(key, any)
        }
        edit.apply()
    }

    fun get(key: String, l: Long): Long = prefs.getLong(key,l)
    fun get(key: String, b: Boolean): Boolean = prefs.getBoolean(key,b)
    fun get(key: String, f: Float): Float = prefs.getFloat(key,f)
    fun get(key: String, str: String): String = prefs.getString(key,str)
    fun get(key: String, set: Set<String>): Set<String> = prefs.getStringSet(key,set)
    fun get(key: String, i : Int): Int = prefs.getInt(key,i)

    fun remove(key: String) = edit.remove(key).apply()
    fun clearAll() = edit.clear().apply()

}