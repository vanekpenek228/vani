package com.example.myapplication.API

import com.example.myapplication.Data.Movie
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface API_Interface {
    @POST("api/SendCodeEmail")
    fun SendCode(@Header("User-email") email: String): Call<ResponseBody>

    @POST("api/SendCodeEmail")
    fun SignIn(@Header("User-email") email: String, @Header("User-code") code: String): Call<ResponseBody>

    @GET("api/Movie")
    fun GetMovies():Call<MutableList<Movie>>
}