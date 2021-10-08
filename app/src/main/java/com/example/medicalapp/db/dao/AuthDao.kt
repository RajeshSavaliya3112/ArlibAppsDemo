package com.example.medicalapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.medicalapp.db.entitys.AuthEntity

@Dao
interface AuthDao {

    @Insert
    fun insertAll(vararg users: AuthEntity)

    @Delete
    fun delete(user: AuthEntity)

    @Query("SELECT * FROM auth")
    fun getAll(): List<AuthEntity>

    @Query("SELECT * FROM auth WHERE email = :username AND password = :password")
    fun checkUser(username: String, password: String) : AuthEntity?

}