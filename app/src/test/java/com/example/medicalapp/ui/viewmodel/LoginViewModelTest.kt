package com.example.medicalapp.ui.viewmodel

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.medicalapp.db.AppDatabase
import com.example.medicalapp.di.DatabaseModule
import com.example.medicalapp.repository.AuthRepository
import com.example.medicalapp.ui.auth.viewmodel.LoginViewModel
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest : TestCase() {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDatabase: AppDatabase

    lateinit var viewModel: LoginViewModel

    @Before
    fun init() {
        hiltRule.inject()
        viewModel = LoginViewModel(AuthRepository(appDatabase))
    }

    @Test
    fun loginTest() {
        runBlocking {
            viewModel.register("test@gmail.com", "123456")

            viewModel.login("test@gmail.com", "123456")

            assertThat(appDatabase.authDao().getAll().find {
                it.email == "test@gmail.com"
            } != null).isTrue()
        }

    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDatabaseModule {

        @Provides
        @Singleton
        fun provideRoomDB(@ApplicationContext appContext: Context) =
            Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "medical-db"
            ).build()

    }
}
