package com.example.whatermelon.repository

import com.example.whatermelon.network.ApiService

class MainRepository constructor(private val retrofitService: ApiService) {

    suspend fun getRandomActivity() = retrofitService.getRandomData()

}