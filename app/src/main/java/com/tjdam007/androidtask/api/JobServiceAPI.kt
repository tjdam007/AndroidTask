package com.tjdam007.androidtask.api


import com.tjdam007.androidtask.model.Job
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JobServiceAPI {
    @GET("positions.json")
    fun searchJobs(@Query("search") query: String): Observable<ArrayList<Job>>

    @GET("positions.json")
    fun getJobsByLocation(
        @Query("location") location: String,
        @Query("page") page: Int
    ): Observable<ArrayList<Job>>
}