package com.flesh.aaronsays.utils

import android.os.Build

/**
 * Where all of the sdk based utils are
 * Created by aaronfleshner on 10/31/17.
 */

/**
 * The SDK int must be higher than or equal to this level
 * usage: Build.VERSION_CODES.M.atLeastSDKlevel
 * usage: 23.atLeastSDKlevel
 */
val Int.atLeastSDKlevel: Boolean get() = Build.VERSION.SDK_INT >= this

/**
 * The SDK int must be below with level
 * usage: Build.VERSION_CODES.M.belowSDKlevel
 * usage: 23.belowSDKlevel
 */
val Int.belowSDKlevel: Boolean get() = Build.VERSION.SDK_INT < this
