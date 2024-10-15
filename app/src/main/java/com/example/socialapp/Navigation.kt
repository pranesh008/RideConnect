package com.example.socialapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            RegisterScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(
                username = "RiderName",
                bio = "Motorcycle enthusiast, adventure lover.",
                followers = 120,
                following = 80,
                onEditProfile = { /* Handle Edit Profile */ }
            ) { /* Handle Follow Button */ }
        }
    }
}