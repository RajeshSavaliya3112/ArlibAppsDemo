package com.example.medicalapp.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalapp.repository.MedicalRepository
import com.example.medicalapp.ui.main.model.AssociatedDrugItem
import com.example.medicalapp.ui.main.model.MedicineModel
import com.example.medicalapp.utils.Resource
import com.example.medicalapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val medicalRepository: MedicalRepository
) : ViewModel() {

    val errorResponse = MutableLiveData<String>()
    val loadingProgress = MutableLiveData<Boolean>()

    val response = MutableLiveData<ArrayList<AssociatedDrugItem>>()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            medicalRepository.fetchMedicine().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        response.postValue(getMedicineFilterData(it))
                        loadingProgress.postValue(false)
                    }
                    Status.ERROR -> {
                        loadingProgress.postValue(false)
                        errorResponse.postValue(it.message.toString())
                    }
                    Status.LOADING -> {
                        loadingProgress.postValue(true)
                    }
                }
            }
        }
    }

    private fun getMedicineFilterData(resource: Resource<Any>) = let {
        val list = ArrayList<AssociatedDrugItem>()
        (resource.data as MedicineModel).let {
            it.problems?.forEach {
                it?.diabetes?.forEach {
                    it?.medications?.forEach {
                        it?.medicationsClasses?.forEach {
                            it?.className?.forEach {
                                it?.associatedDrug?.forEach {
                                    it?.let {
                                        list.add(it)
                                    }
                                }
                                it?.associatedDrug2?.map {
                                    AssociatedDrugItem(
                                        it?.dose,
                                        it?.strength,
                                        it?.name
                                    )
                                }?.forEach {
                                    list.add(it)
                                }
                            }
                            it?.className2?.forEach {
                                it?.associatedDrug?.forEach {
                                    it?.let {
                                        list.add(it)
                                    }
                                }
                                it?.associatedDrug2?.map {
                                    AssociatedDrugItem(
                                        it?.dose,
                                        it?.strength,
                                        it?.name
                                    )
                                }?.forEach {
                                    list.add(it)
                                }
                            }
                        }
                    }
                }
            }
        }
        list
    }

}