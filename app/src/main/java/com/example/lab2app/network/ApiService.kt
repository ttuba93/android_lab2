package com.example.lab2app.network

import com.example.lab2app.model.Animal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("animals")
    fun getAnimals(
        @Query("name") name: String,
        @Query("apiKey") apiKey: String
    ): Call<List<Animal>>


}
