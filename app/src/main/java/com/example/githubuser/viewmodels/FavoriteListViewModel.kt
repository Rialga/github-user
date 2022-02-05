package com.example.githubuser.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.models.GithubUserFavoriteModel
import com.example.githubuser.repository.GithubUserFavoriteRepository

class FavoriteListViewModel(application : Application) : ViewModel() {
     private val mGithubUserFavoriteRepository: GithubUserFavoriteRepository = GithubUserFavoriteRepository(application)

     fun getAllList(): LiveData<List<GithubUserFavoriteModel>> = mGithubUserFavoriteRepository.getAllList()

}