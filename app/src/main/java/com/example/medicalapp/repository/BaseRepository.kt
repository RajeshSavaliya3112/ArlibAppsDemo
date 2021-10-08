package com.example.medicalapp.repository

import com.example.medicalapp.network.ApiService
import com.example.medicalapp.utils.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepository(private val apiService: ApiService) {

    protected suspend fun <T> executeApiCall(
        body: suspend () -> T
    ) = flow {
        try {
            emit(Resource.loading(data = null))
            val model = body()
            if (model is Response<*>) {
                if (model.isSuccessful)
                    emit(Resource.success(data = model.body()))
                else
                    emit(Resource.error(model.errorBody().toString(),null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if(e.message?.contains("No address associated with hostname") == true)
                emit(Resource.error("No Internet Connection",null))
            else
            emit(Resource.error(e.message.toString(),null))
        }
    }
}