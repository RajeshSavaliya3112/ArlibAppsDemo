package com.example.medicalapp.repository

import com.example.medicalapp.network.ApiService
import javax.inject.Inject

class MedicalRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository(apiService) {

    suspend fun fetchMedicine() = executeApiCall {
        apiService.fetchMedicine()
    }

}
