package com.example.medicalapp.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.medicalapp.db.dao.AuthDao
import com.example.medicalapp.db.entitys.AuthEntity
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: AuthDao


    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.authDao()
    }


    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun writeAndReadSpend() {
        val authData = AuthEntity(0, "rajesh@gmail.com", "Rajesh@123")
        dao.insertAll(authData)
        val auth = dao.getAll()
        assertThat(auth.contains(authData)).isTrue()
    }
}