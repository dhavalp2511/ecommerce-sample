package com.example.ecommercedemo.data.api.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class GetProfileResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("pointsEarned")
    val pointsEarned: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("walletBalance")
    val walletBalance: String?
) : Parcelable