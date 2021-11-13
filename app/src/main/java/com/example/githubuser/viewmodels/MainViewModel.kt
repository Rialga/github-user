package com.example.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubuser.localdata.SettingPreferences
import com.example.githubuser.models.GithubUserModel
import com.example.githubuser.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val pref: SettingPreferences): ViewModel() {

    val gitHubUserModel = MutableLiveData<ArrayList<GithubUserModel.Item>>()
    private val retrofitService = RetrofitService


    fun getGithubUserList(username: String) {
        val query: String = if (username == "") {"a"} else {username}

        val response = retrofitService.getInstance().searchGithubUser(query)
        response.enqueue(object : Callback<GithubUserModel> {
            override fun onResponse(
                call: Call<GithubUserModel>,
                response: Response<GithubUserModel>
            ) {

                if (response.isSuccessful) {
                    gitHubUserModel.postValue(response.body()?.items)
                } else {
                    Log.d("Error Response", response.errorBody().toString() + "error ini")
                }

            }

            override fun onFailure(call: Call<GithubUserModel>, t: Throwable) {
                Log.d("err vm ", t.message.toString())
            }
        })

    }

    fun getData(): MutableLiveData<ArrayList<GithubUserModel.Item>> {
        return gitHubUserModel
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }


}