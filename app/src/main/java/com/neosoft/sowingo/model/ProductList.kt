package com.neosoft.sowingo.model

import com.google.gson.annotations.SerializedName


data class ProductList (

  @SerializedName("hits" ) var hits : ArrayList<Hits> = arrayListOf()

)