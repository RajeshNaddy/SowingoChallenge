package com.neosoft.sowingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neosoft.sowingo.model.Hits
import com.neosoft.sowingo.network.NetworkState
import com.neosoft.sowingo.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val productList = MutableLiveData<ArrayList<Hits>>()

    val loading = MutableLiveData<Boolean>()

    /**
     * Function to fetch all the products via API
     * **/
    fun getAllProducts() {
        loading.value = true
        viewModelScope.launch {
            when (val response = productRepository.getAllProducts()) {
                is NetworkState.Success -> {
                    loading.value = false
                    productList.postValue(response.data.hits)
                }
                is NetworkState.Error -> {
                    loading.value = false
                    onError("Something went wrong!")
                }
            }
        }
    }

    /**
     * Function to send back error msg if something wrong
     * **/
    private fun onError(message: String) {
        _errorMessage.value = message
        loading.value = false
    }

}