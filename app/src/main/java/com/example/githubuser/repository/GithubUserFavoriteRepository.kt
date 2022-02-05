package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.GithubUserFavoriteDao
import com.example.githubuser.database.GithubUserFavoriteRoomDatabase
import com.example.githubuser.models.GithubUserFavoriteModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubUserFavoriteRepository(application: Application) {
    private val mGithubUserFavoriteDao: GithubUserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = GithubUserFavoriteRoomDatabase.getDatabase(application)
        mGithubUserFavoriteDao = db.githubUserFavoriteDao()
    }

    fun getAllList(): LiveData<List<GithubUserFavoriteModel>> = mGithubUserFavoriteDao.getAllGithubUserFavorite()

    fun insert(user: GithubUserFavoriteModel) {
        executorService.execute { mGithubUserFavoriteDao.insert(user) }
    }
    fun delete(user: GithubUserFavoriteModel) {
        executorService.execute { mGithubUserFavoriteDao.delete(user) }
    }

    fun search(login: String) : LiveData<List<GithubUserFavoriteModel>> = mGithubUserFavoriteDao.search(login)

}