@file:Suppress("DEPRECATION")

package com.example.socialapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navigation
import com.example.socialapp.R
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    var errorMessage by remember {mutableStateOf("")}
    var passwordVisible by remember {mutableStateOf(false)}
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
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
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                )
            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = {
                    // Validate input fields
                    if (mobileNumber.isEmpty() || password.isEmpty()) {
                        errorMessage = "Enter both Mobile number and Password"
                    } else {
                        // If inputs are valid, attempt to log in
                        val dbHelper = DatabaseHelper(context)
                        val userExists = dbHelper.checkUserCredentials(mobileNumber, password)

                        if (userExists) {
                            // Navigate to the next screen or show success message
                            errorMessage = "Login successful"
                            navController.navigate("home") {// Navigate to home screen
                                popUpTo("login") { inclusive = true } //remove login from back stack
                            }
                        }else {
                            // Show error message if user not found
                            errorMessage = "Invalid mobile number or password"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Create New Account Button
            TextButton(onClick = { navController.navigate("register") }) {
                val annotatedText = buildAnnotatedString {
                    append("Don't have an account? ")

                    // Create a hyperlink style for the word "Register"
                    pushStringAnnotation(tag = "register", annotation = "register")
                    withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                        append("Register")
                    }
                    pop()
                }
                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontSize = 16.sp),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "register", start = offset, end = offset)
                            .firstOrNull()?.let {
                                navController.navigate("register")  // Navigate to the Register screen
                            }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}
