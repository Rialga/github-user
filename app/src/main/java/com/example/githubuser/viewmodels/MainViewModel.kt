package com.example.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.models.GithubUserDetailModel
import com.example.githubuser.models.GithubUserModel
import com.example.githubuser.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val gitHubUserDetailModel = MutableLiveData<ArrayList<GithubUserDetailModel>>()
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
                    Log.d("success vm ", response.body().toString())
                    val list = arrayListOf<GithubUserDetailModel>()

                    response.body()?.items?.forEach { item ->
                        val responseDetail = retrofitService.getInstance().getDetail(item.login)

                        responseDetail.enqueue(object : Callback<GithubUserDetailModel> {
                            override fun onResponse(
                                call: Call<GithubUserDetailModel>,
                                response: Response<GithubUserDetailModel>
                            ) {
                                list += response.body()!!
                                gitHubUserDetailModel.postValue(list)
                            }

                            override fun onFailure(
                                call: Call<GithubUserDetailModel>, t: Throwable
                            ) {
                                Log.d("err detail vm ", t.message.toString())
                            }
                        })
                    }

                } else {
                    Log.d("Error Response", response.errorBody().toString() + "error ini")
                }


            }

            override fun onFailure(call: Call<GithubUserModel>, t: Throwable) {
                Log.d("err vm ", t.message.toString())
            }
        })

    }

    fun getData(): LiveData<ArrayList<GithubUserDetailModel>> {
        return gitHubUserDetailModel
    }
}