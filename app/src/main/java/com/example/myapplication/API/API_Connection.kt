package com.example.myapplication.API

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API_Connection {
    val link_api:String = "https://iis.ngknn.ru/NGKNN/МамшеваЮС/exam/"
    val gson = GsonBuilder().setLenient().create()
    var email: String? = null
    fun getConnection(): API_Interface{
        return Retrofit.Builder()
            .baseUrl(link_api)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(API_Interface::class.java)
    }
}