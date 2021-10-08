package com.example.medicalapp.utils

import android.content.Context
import android.os.Build
import android.util.Patterns
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


/**
 * this methods is use to get color from resource
 */
fun Context.rColor(@ColorRes id: Int) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    getColor(id)
else
    ContextCompat.getColor(this, id)


fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(this, message, duration).show()