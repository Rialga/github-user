package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.models.GithubUserFavoriteModel

@Dao
interface GithubUserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(githubUserFavoriteModel: GithubUserFavoriteModel)

    @Delete
    fun delete(githubUserFavoriteModel: GithubUserFavoriteModel)

    @Query("SELECT * from githubUserFavoriteModel ORDER BY id ASC")
    fun getAllGithubUserFavorite(): LiveData<List<GithubUserFavoriteModel>>

    @Query("SELECT * from githubUserFavoriteModel WHERE login IN(:login)")
    fun search(login: String?): LiveData<List<GithubUserFavoriteModel>>

}