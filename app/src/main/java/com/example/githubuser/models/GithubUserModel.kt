package com.example.githubuser.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigInteger

data class GithubUserModel(
    val total_count : BigInteger?,
    val incomplete_results : Boolean?,
    val items: ArrayList<Item>
) {
    @Parcelize
    data class Item(
        val id: Int,
        val login: String,
        var avatar_url : String,
    ): Parcelable
}