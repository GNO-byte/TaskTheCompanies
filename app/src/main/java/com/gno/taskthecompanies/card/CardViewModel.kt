package com.gno.taskthecompanies.card

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gno.taskthecompanies.base.BaseViewModel
import com.gno.taskthecompanies.retrofit.Api
import com.gno.taskthecompanies.retrofit.data.CompanyData
import kotlinx.coroutines.launch

class CardViewModel : BaseViewModel() {

    private val TAG = "CardViewModel"

    val companyDataLiveData = MutableLiveData<CompanyData>()
    private val emptyCompanyData = CompanyData("", "", "", 0.0, 0.0, "", "", "")
    private var companyData: CompanyData = emptyCompanyData

    fun getCompanyData(id: String) {
        scope.launch {

            companyData = when (id) {
                companyData.id -> companyData
                "" -> emptyCompanyData
                else -> try {
                    val r = Api.retrofitService.getСompanyData(id)
                    r[0]
                } catch (e: Exception) {
                    Log.e(TAG, e.message, e)
                    emptyCompanyData
                }
            }
            companyDataLiveData.postValue(companyData)
        }
    }

}
