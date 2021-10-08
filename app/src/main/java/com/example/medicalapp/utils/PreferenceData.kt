package com.example.medicalapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.medicalapp.db.entitys.AuthEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val PREFERENCE_NAME = "user_pref"

const val PREFERENCE_LOGIN_DATA = "login_data"

private val Context.dataStore: SharedPreferences
    get() = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)


fun Context.loginData(auth: AuthEntity) =
    dataStore.edit().putString(PREFERENCE_LOGIN_DATA, Gson().toJson(auth)).apply()


fun Context.loginData() =
    Gson().fromJson<AuthEntity>(dataStore.getString(PREFERENCE_LOGIN_DATA, ""),
        object : TypeToken<AuthEntity>() {}.type)
