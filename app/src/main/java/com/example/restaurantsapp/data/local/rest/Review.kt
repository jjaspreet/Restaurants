package com.example.restaurantsapp.data.local.rest

data class Review(
    val comments: String,
    val date: String,
    val name: String,
    val rating: Int
)