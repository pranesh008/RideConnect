package com.example.socialapp.data

data class User(
    val name: String,
    val ridingExperience: Int,
    val favoriteRoute: String,  // Ensure this field matches the return statement
    val isFollowed: Boolean      // Ensure this field matches the return statement
)
