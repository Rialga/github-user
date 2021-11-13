package com.example.githubuser.services


import com.example.githubuser.models.GithubUserDetailModel
import com.example.githubuser.models.GithubUserModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(@Path("username") username: String?): Call<GithubUserDetailModel>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchGithubUser( @Query("q") q: String) : Call <GithubUserModel>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollower(@Path("username") username: String?): Call<ArrayList<GithubUserModel.Item>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String?): Call<ArrayList<GithubUserModel.Item>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}