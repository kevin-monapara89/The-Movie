package com.kevin.themovie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("upcoming")
    fun getupcoming(
        @Query ("page")page:Int) :Call<MovieModel>
}