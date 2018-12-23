package com.teddy.sweatcontacts.common.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.teddy.sweatcontacts.common.view.Resource
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.model.Contact
import kotlinx.coroutines.coroutineScope

const val SEARCH_SEED = "teddy"
const val SEARCH_AMOUNT = 50

class ContactRemoteSource(private val service: ContactService) {

    private val _result = MutableLiveData<Resource<List<Contact>>>()
    val result
        get() = _result as LiveData<Resource<List<Contact>>>

    suspend fun getContacts() = coroutineScope {
        _result.value = Resource.loading(null)
        callContacts(1, Status.SUCCESS)
    }

    suspend fun getMoreContacts(page: Int) {
        _result.value = Resource.loadingMore(null)
        callContacts(page, Status.SUCCESS_MORE)
    }

    private suspend fun callContacts(page: Int, successStatus: Status) {
        val response = service.getContacts(SEARCH_AMOUNT, SEARCH_SEED, page).await()
        when (response) {
            is ApiSuccessResponse -> {
                _result.postValue(Resource.success(successStatus, response.body.results.map { it.toContact() }))
            }
            is ApiEmptyResponse -> {
                _result.postValue(Resource.error("No joke", null))
            }
            is ApiErrorResponse -> {
                println("TEDDY> ERROR: ${response.errorMessage}")
                _result.postValue(Resource.error(response.errorMessage, null))
            }
        }
    }
}