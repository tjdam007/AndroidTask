package com.tjdam007.androidtask.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val baseUrl = "https://jobs.github.com"
    val jobServiceAPI: JobServiceAPI

    init {
        jobServiceAPI = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(JobServiceAPI::class.java)
    }
}