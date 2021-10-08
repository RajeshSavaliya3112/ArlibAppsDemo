package com.example.medicalapp.repository

import com.example.medicalapp.db.AppDatabase
import com.example.medicalapp.db.entitys.AuthEntity
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val roomDb: AppDatabase
) {

    fun loginUser(username: String, password: String) =
        roomDb.authDao().checkUser(username, password)

    fun registerUser(username: String, password: String) =
        roomDb.authDao().insertAll(AuthEntity(0,username, password))
}