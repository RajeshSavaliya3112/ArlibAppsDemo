package com.example.medicalapp.utils

import android.content.Context
import com.example.medicalapp.R
import java.util.*


/**
 * this method is use to get greeting from current local time
 * [return] greeting
 */
fun Context.getGreetingMessage() = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
    in 0..11 -> getString(R.string.good_morning)
    in 12..15 -> getString(R.string.good_afternoon)
    in 16..20 -> getString(R.string.good_evening)
    in 21..24 -> getString(R.string.good_night)
    else -> ""
}

