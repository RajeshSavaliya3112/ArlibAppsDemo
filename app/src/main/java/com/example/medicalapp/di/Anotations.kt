package com.example.medicalapp.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpClientWithSSL

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OkHttpClientWithoutSSL