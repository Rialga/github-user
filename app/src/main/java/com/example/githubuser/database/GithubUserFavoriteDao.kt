package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GithubUserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(githubUserFavorite: GithubUserFavorite)

    @Delete
    fun delete(githubUserFavorite: GithubUserFavorite)

    @Query("SELECT * from GithubUserFavorite ORDER BY id ASC")
    fun getAllGithubUserFavorite(): LiveData<List<GithubUserFavorite>>

}