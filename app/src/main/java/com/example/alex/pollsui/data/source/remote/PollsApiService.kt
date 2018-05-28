package com.example.alex.pollsui.data.source.remote

import android.util.Base64
import com.example.alex.pollsui.data.Poll
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PollsApiService {

    @GET("getPollsList")
    fun getPollsList(): Call<List<Poll>>

    @GET("getMyPollsList")
    fun getMyPollsList(): Call<List<Poll>>

    @GET("getPoll")
    fun getPoll(@Query("id") id: String?): Call<Poll>

    @GET("getPollWithStats")
    fun getPollWithStats(@Query("id") id: String?): Call<Poll>

    @POST("createPoll")
    fun createPoll(@Body poll: Poll): Call<Poll>

    @POST("result")
    fun submitPoll(@Body poll: Poll): Call<Poll>

    companion object Factory {

        fun create(): PollsApiService {
            val credentials = "user2:password"
            val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

            val httpClient = OkHttpClient.Builder()
                    .addInterceptor {
                        it.proceed(it.request()
                                .newBuilder()
                                .addHeader("Authorization", basic)
                                .build())
                    }
                    .build()

            return Retrofit.Builder()
                    .baseUrl("https://warm-retreat-17416.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
                    .create(PollsApiService::class.java)
        }
    }
}
