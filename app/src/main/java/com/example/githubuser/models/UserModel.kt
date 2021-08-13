package com.example.githubuser.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val username: String?,
    val name: String?,
    val location: String?,
    val company: String?,
    val avatar: Int,
    val follower: String,
    val following: String,
    val repository: String,
) : Parcelable

