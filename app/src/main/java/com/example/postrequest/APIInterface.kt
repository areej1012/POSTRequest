package com.example.postrequest

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @POST("test/")
    fun addItem(@Body itemData :PostItem) : Call<PostItem>
    @GET("test/")
    fun getItem(): Call<GetItem>
    @DELETE("test/{pk}")
    fun deleteItem(@Path("pk") pk : Int): Call<Void>
    @PUT("test/{pk}")
    fun updateItem(@Path("pk") pk : Int, @Body itemDate : PostItem) : Call<PostItem>
}