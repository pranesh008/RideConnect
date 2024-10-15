package com.example.socialapp.repository

import com.example.socialapp.data.User

object UserDataRepository {
    fun getUser(userId: Int): User {
        // Mock data, replace with actual data fetching logic
        return User(
            name = "John Doe",
            ridingExperience = 5,
            favoriteRoute = "Mountain Pass",  // Field name matches with the data class
            isFollowed = false                // Field name matches with the data class
        )
    }
}
