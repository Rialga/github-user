package com.example.githubuser.models


import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class GithubUserModel(
    val total_count : BigInteger?,
    val incomplete_results : Boolean?,
    val items: ArrayList<Item>
) {
    data class Item(
        val id: Int,
        val login: String,
    )
}