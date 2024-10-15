package com.example.socialapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeScreen(navController: NavController) {
    var posts by remember { mutableStateOf(listOf<Post>()) }

    LaunchedEffect(Unit) {
        // Fetch posts from API or database
        posts = listOf(
            Post("Post 1", "This is the first post"),
            Post("Post 2", "This is the second post"),
            Post("Post 3", "This is the third post")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Motorcycling Social Media App",
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(posts) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = post.title,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.content,
            )
        }
    }
}

data class Post(val title: String, val content: String)

@Preview
@Composable
fun preview(){
    HomeScreen(navController = rememberNavController())
}