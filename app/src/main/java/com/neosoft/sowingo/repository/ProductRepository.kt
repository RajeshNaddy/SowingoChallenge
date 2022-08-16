package com.neosoft.sowingo.repository

import com.neosoft.sowingo.model.ProductList
import com.neosoft.sowingo.network.NetworkState
import com.neosoft.sowingo.network.RetrofitService

class ProductRepository constructor(private val retrofitService: RetrofitService) {

    /**
     * Function to fetch all the products via API
     * **/
    suspend fun getAllProducts(): NetworkState<ProductList> {
        val response = retrofitService.getAllProducts()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}
