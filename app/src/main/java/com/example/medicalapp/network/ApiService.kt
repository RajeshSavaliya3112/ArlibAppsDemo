package com.example.medicalapp.network

import com.example.medicalapp.ui.main.model.MedicineModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v3/de4bdc4b-2793-406f-8b1c-21cd75e4f72b")
    suspend fun fetchMedicine() : Response<MedicineModel>
    
}