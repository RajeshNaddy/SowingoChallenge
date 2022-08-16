package com.neosoft.sowingo.model

import com.google.gson.annotations.SerializedName


data class Hits(

    @SerializedName("unit_name") var unitName: String? = null,
    @SerializedName("vendor_inventory") var vendorInventory: ArrayList<VendorInventory> = arrayListOf(),
    @SerializedName("is_favourite_product") var isFavouriteProduct: Boolean? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("main_image") var mainImage: String? = null,
    @SerializedName("name") var name: String? = null,
    var isFavourite: Boolean = false

)