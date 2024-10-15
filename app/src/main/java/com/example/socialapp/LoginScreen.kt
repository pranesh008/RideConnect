package com.example.socialapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialapp.R

@Composable
fun LoginScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    var errorMessage by remember {mutableStateOf("")}
    var passwordVisible by remember {mutableStateOf(false)}
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Using Box for stacking elements
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image with dark overlay
        Image(
            painter = painterResource(R.drawable.login_page),
            contentDescription = "Motorcycle Background",
            modifier = Modifier.fillMaxSize(),  // Make the image fill the whole screen
            contentScale = ContentScale.Crop,    // Adjust the image to fill the space while cropping if necessary
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )

        // Login form on top of the image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center the form vertically
        ) {
            // Login Title
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold,
                    color = Color.White    ),
                  // Set text color to white to contrast with background
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mobile Number Input
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile Number", color = Color.White) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        painterResource(R.drawable.visibility)
                    else
                        painterResource(R.drawable.visibility_off)

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, contentDescription = null, tint = Color.White)
                    }
                },)
            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = {
                          //validate input
                          if (mobileNumber.isEmpty() || password.isEmpty()) {
                              errorMessage = "Enter both Mobile number and Password"
                          } else {
                              errorMessage = ""
                          //TODO: perform login logic
                          }
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Create New Account Button
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Don't have an account? Register", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}
