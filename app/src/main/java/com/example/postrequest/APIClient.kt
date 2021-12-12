package com.example.postrequest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    private var retrofit: Retrofit? =null

    fun getItem(): Retrofit?{
        retrofit = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com/")
            //convert the json to model
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}