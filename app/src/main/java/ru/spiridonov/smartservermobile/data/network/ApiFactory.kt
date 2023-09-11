package ru.spiridonov.smartservermobile.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    //private const val BASE_URL = "http://77.51.185.88:9888/api/"
    //private const val BASE_URL = "http://172.20.10.2:8080/api/"
    private const val BASE_URL = "http://192.168.10.76:8080/api/"
    private val json = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(json))
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}