package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class GithubUserFavorite (

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var user_id: Int = 0,

    @ColumnInfo(name = "description")
    var login: String? = null,

    @ColumnInfo(name = "date")
    var avatar_url: String? = null


): Parcelable