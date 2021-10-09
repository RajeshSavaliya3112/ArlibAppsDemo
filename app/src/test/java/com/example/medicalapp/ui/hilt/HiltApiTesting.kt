package com.example.medicalapp.ui.hilt

import com.example.medicalapp.BuildConfig
import com.example.medicalapp.di.OkHttpClientWithSSL
import com.example.medicalapp.di.OkHttpClientWithoutSSL
import com.example.medicalapp.di.RetrofitModule
import com.example.medicalapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@UninstallModules(RetrofitModule::class)
@HiltAndroidTest
open class HiltApiTesting {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ApiService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun someTest() = runBlocking {
        val apiResponse = apiService.fetchMedicine()
        assert(apiResponse.isSuccessful)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestRetrofitModule {

        @Provides
        @Singleton
        @OkHttpClientWithSSL
        fun provideOkHttpClient() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        @Provides
        @Singleton
        @OkHttpClientWithoutSSL
        fun provideOkHttpClientWithoutSSL(): OkHttpClient {
            return try {

                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()

                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }

                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @Provides
        @Singleton
        fun provideRetrofit(@OkHttpClientWithoutSSL okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit) =
            retrofit.create(ApiService::class.java)
    }
}