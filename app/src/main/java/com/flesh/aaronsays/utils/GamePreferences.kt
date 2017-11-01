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

    /**
     * Saves any to game preferences
     * @param key the key used to save the object.
     * @param any value that is being saved. (Long,Boolean,Float,String,Set<String>,Int) Supported
     */
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

    /**
     * @param key the key used to get the object.
     * @param l value that is being gotten.
     * Gets a Long
     */
    fun get(key: String, l: Long): Long = prefs.getLong(key,l)
    /**
     * @param key the key used to get the object.
     * @param b value that is being gotten.
     * Gets a Boolean
     */
    fun get(key: String, b: Boolean): Boolean = prefs.getBoolean(key,b)
    /**
     * @param key the key used to get the object.
     * @param f value that is being gotten.
     * Gets a Float
     */
    fun get(key: String, f: Float): Float = prefs.getFloat(key,f)
    /**
     * @param key the key used to get the object.
     * @param str value that is being gotten.
     * Gets a String
     */
    fun get(key: String, str: String): String = prefs.getString(key,str)
    /**
     * @param key the key used to get the object.
     * @param set value that is being gotten.
     * Gets a String Set
     */
    fun get(key: String, set: Set<String>): Set<String> = prefs.getStringSet(key,set)
    /**
     * @param key the key used to get the object.
     * @param i value that is being gotten.
     * Gets a Int
     */
    fun get(key: String, i : Int): Int = prefs.getInt(key,i)
    /**
     * @param key key for value being removed
     * Removes a value for key from game prefreneces
     */
    fun remove(key: String) = edit.remove(key).apply()

    /**
     * Clears the game preferences
     */
    fun clearAll() = edit.clear().apply()

}