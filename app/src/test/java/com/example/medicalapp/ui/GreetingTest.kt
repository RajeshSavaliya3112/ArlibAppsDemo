package com.example.medicalapp.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.medicalapp.utils.getGreetingMessage
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GreetingTest : TestCase() {

    @Test
    fun greetingTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        assertThat(context.getGreetingMessage(),containsString("Good Morning"))
    }

}