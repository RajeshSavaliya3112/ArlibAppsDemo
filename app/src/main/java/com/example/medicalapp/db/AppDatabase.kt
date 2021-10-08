package com.example.medicalapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.medicalapp.db.dao.AuthDao
import com.example.medicalapp.db.entitys.AuthEntity

@Database(entities = [AuthEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authDao() : AuthDao

}