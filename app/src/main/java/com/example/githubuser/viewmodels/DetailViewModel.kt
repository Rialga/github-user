package com.example.githubuser.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.models.GithubUserDetailModel
import com.example.githubuser.models.GithubUserFavoriteModel
import com.example.githubuser.models.GithubUserModel
import com.example.githubuser.repository.GithubUserFavoriteRepository
import com.example.githubuser.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application : Application) : ViewModel() {
        val followerData = MutableLiveData<ArrayList<GithubUserModel.Item>>()
        val followingData = MutableLiveData<ArrayList<GithubUserModel.Item>>()
    val detailData = MutableLiveData<GithubUserDetailModel>()

    private val retrofitService = RetrofitService
    private val mGithubUserFavoriteRepository: GithubUserFavoriteRepository = GithubUserFavoriteRepository(application)


    fun getFollower(username : String){
        val responseFollower = retrofitService.getInstance().getFollower(username)

        responseFollower.enqueue(object : Callback<ArrayList<GithubUserModel.Item>>{
            override fun onResponse(
                call: Call<ArrayList<GithubUserModel.Item>>,
                response: Response<ArrayList<GithubUserModel.Item>>
            ) {
                if (response.isSuccessful) {
                    followerData.postValue(response.body())

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
        val responseFollowing = retrofitService.getInstance().getFollowing(username)

        responseFollowing.enqueue(object : Callback<ArrayList<GithubUserModel.Item>>{
            override fun onResponse(
                call: Call<ArrayList<GithubUserModel.Item>>,
                response: Response<ArrayList<GithubUserModel.Item>>
            ) {
                if (response.isSuccessful) {
                    followingData.postValue(response.body())

                } else {
                    Log.d("Error Response", response.errorBody().toString() + "error ini")
                }
            }

            override fun onFailure(call: Call<ArrayList<GithubUserModel.Item>>, t: Throwable) {
                Log.d("err vm ", t.message.toString())
            }

        })
    }

    fun getData(username : String){
        val responseFollowing = retrofitService.getInstance().getDetail(username)

        responseFollowing.enqueue(object : Callback<GithubUserDetailModel>{
            override fun onResponse(
                call: Call<GithubUserDetailModel>,
                response: Response<GithubUserDetailModel>
            ) {
                detailData.postValue(response.body())
            }

            override fun onFailure(call: Call<GithubUserDetailModel>, t: Throwable) {
                Log.d("Error Response", t.message + "error ini")
            }

        })
    }

    fun insert(user: GithubUserFavoriteModel) {
        mGithubUserFavoriteRepository.insert(user)
    }

    fun delete(user: GithubUserFavoriteModel) {
        mGithubUserFavoriteRepository.delete(user)
    }

    fun search(login:String): LiveData<List<GithubUserFavoriteModel>> {

        return  mGithubUserFavoriteRepository.search(login)
    }


    fun getDataFollowing(): LiveData<ArrayList<GithubUserModel.Item>> {
        return followingData
    }
    fun getDataFollower(): LiveData<ArrayList<GithubUserModel.Item>> {
        return followerData
    }

    fun getDetail(): LiveData<GithubUserDetailModel> {
        return detailData
    }


}