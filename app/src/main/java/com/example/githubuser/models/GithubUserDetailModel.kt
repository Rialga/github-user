package com.example.githubuser.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GithubUserDetailModel (
    val id: Int,
    val login: String?,
    val name: String?,
    val location: String?,
    val company: String?,
    val avatar_url: String?,
    val followers: String,
    val following: String,
    val public_repos: String

):Parcelable