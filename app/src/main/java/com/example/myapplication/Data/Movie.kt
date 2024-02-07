package com.example.myapplication.Data

data class Movie(var id: Int = 0,
var title: String? = null,
var description: String? = null,
var ratingImdb: Float = 0f,
var ratingKinopoisk: Float = 0f,
var director: String? = null,
var countryMovie: String? = null,
var genres: MutableList<String>)
