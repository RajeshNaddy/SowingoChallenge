package com.neosoft.sowingo.model

import com.google.gson.annotations.SerializedName


data class VendorInventory(
    @SerializedName("vendor_inventory_id") var vendorInventoryId: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("list_price") var list_price: String? = null,
)