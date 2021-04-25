package com.gno.taskthecompanies.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gno.taskthecompanies.base.BaseViewModel
import com.gno.taskthecompanies.retrofit.Api
import com.gno.taskthecompanies.retrofit.data.Company
import kotlinx.coroutines.launch

class ListViewModel : BaseViewModel() {

    private val TAG = "ListViewModel"

    //LiveDat
    val companiesLiveData = MutableLiveData<List<Company>>()
    private val companies = ArrayList<Company>()

    fun getCompanies() {
        val n = 1
        scope.launch {
            if (companies.size == 0)
                try {
                    companies.addAll(Api.retrofitService.getCompanies())
                } catch (e: Exception) {
                    Log.e(TAG, e.message, e)
                }
            companiesLiveData.postValue(companies.toList())
        }
    }

    fun getCopiesCompanies() {
        scope.launch {
            companies.addAll(Api.retrofitService.getCompanies())
            companiesLiveData.postValue(companies.toList())
        }
    }


}