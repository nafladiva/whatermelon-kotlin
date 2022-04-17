package com.example.whatermelon.network

import com.example.whatermelon.model.Activity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://www.boredapi.com/api/"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface ApiService {
    @GET("activity")
    suspend fun getRandomData() : Response<Activity>

    companion object {
        var retrofitService: ApiService? = null
        fun getInstance() : ApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }
    }
}

object ActivityApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}