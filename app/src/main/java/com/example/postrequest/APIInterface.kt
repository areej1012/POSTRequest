package com.example.postrequest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @POST("test/")
    fun addItem(@Body itemData :PostItem) : Call<PostItem>
    @GET("test/")
    fun getItem(): Call<GetItem>
}