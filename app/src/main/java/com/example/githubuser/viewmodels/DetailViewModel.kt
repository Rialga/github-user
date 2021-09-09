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

class DetailViewModel : ViewModel() {
    val followerData = MutableLiveData<ArrayList<GithubUserDetailModel>>()
    val followingData = MutableLiveData<ArrayList<GithubUserDetailModel>>()
    private val retrofitService = RetrofitService


    fun getFollower(username : String){
        val response_follower = retrofitService.getInstance().getFollower(username)

        response_follower.enqueue(object : Callback<ArrayList<GithubUserModel.Item>>{
            override fun onResponse(
                call: Call<ArrayList<GithubUserModel.Item>>,
                response: Response<ArrayList<GithubUserModel.Item>>
            ) {
                if (response.isSuccessful) {
                    Log.d("success vm ", response.body().toString())
                    val list_follower = arrayListOf<GithubUserDetailModel>()

                    response.body()?.forEach { item ->
                        val response_detail_follower = retrofitService.getInstance().getDetail(item.login)

                        response_detail_follower.enqueue(object : Callback<GithubUserDetailModel> {
                            override fun onResponse(
                                call: Call<GithubUserDetailModel>,
                                response: Response<GithubUserDetailModel>
                            ) {
                                list_follower += response.body()!!
                                followerData.postValue(list_follower)
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

            override fun onFailure(call: Call<ArrayList<GithubUserModel.Item>>, t: Throwable) {
                Log.d("err vm ", t.message.toString())
            }

        })
    }


    fun getFollowing(username : String){
        val response_following = retrofitService.getInstance().getFollowing(username)

        response_following.enqueue(object : Callback<ArrayList<GithubUserModel.Item>>{
            override fun onResponse(
                call: Call<ArrayList<GithubUserModel.Item>>,
                response: Response<ArrayList<GithubUserModel.Item>>
            ) {
                if (response.isSuccessful) {
                    Log.d("success vm ", response.body().toString())
                    val list_following = arrayListOf<GithubUserDetailModel>()

                    response.body()?.forEach { item ->
                        val response_detail_following = retrofitService.getInstance().getDetail(item.login)

                        response_detail_following.enqueue(object : Callback<GithubUserDetailModel> {
                            override fun onResponse(
                                call: Call<GithubUserDetailModel>,
                                response: Response<GithubUserDetailModel>
                            ) {
                                list_following += response.body()!!
                                followingData.postValue(list_following)
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

            override fun onFailure(call: Call<ArrayList<GithubUserModel.Item>>, t: Throwable) {
                Log.d("err vm ", t.message.toString())
            }

        })
    }

    fun getDataFollowing(): LiveData<ArrayList<GithubUserDetailModel>> {
        return followingData
    }
    fun getDataFollower(): LiveData<ArrayList<GithubUserDetailModel>> {
        return followerData
    }

}