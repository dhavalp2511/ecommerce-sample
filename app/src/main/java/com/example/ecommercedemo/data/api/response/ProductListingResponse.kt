package com.example.ecommercedemo.data.api.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class ProductListingResponse(
    @SerializedName("data")
    val data: Data?
) : Parcelable {
    @Keep
    @Parcelize
    data class Data(
        @SerializedName("products")
        val products: List<Product> = listOf()
    ) : Parcelable {
        @Keep
        @Parcelize
        data class Product(
            @SerializedName("brand")
            val brand: String?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("offerPrice")
            val offerPrice: String?,
            @SerializedName("price")
            val price: String?,
            @SerializedName("productDesc")
            val productDesc: String?,
            @SerializedName("productUrl")
            val productUrl: String?
        ) : Parcelable
    }
}