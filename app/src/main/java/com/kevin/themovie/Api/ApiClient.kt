package com.kevin.themovie.Api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object{

        val Base_Url = "https://api.themoviedb.org/3/movie/"
        val posterUrl = "https://image.tmdb.org/t/p/w500"
        var token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZjRjYjY5YjY0ZWFlYmFjMWI2YzkzYjc0MzI5ZWI2YSIsInN1YiI6IjY0YWE3Mjk4M2UyZWM4MDBjYmNkZGUzOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KBcynMjpJEq69_AvgGFXgYP-qUag6KgLoTIz3TlM2N0"

        var retrofit : Retrofit? = null

        fun getApiClient() : Retrofit{

            if(retrofit == null) {

                retrofit = Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient().newBuilder().addInterceptor{Chain ->
                        val request = Chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token").build()
                        Chain.proceed(request)
                    }.build())
                    .build()
            }
            return retrofit!!
        }
    }
}