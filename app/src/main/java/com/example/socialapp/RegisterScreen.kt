package com.example.socialapp

import android.util.Patterns
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.socialapp.R
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.VisualTransformation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // State variables for error messages
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var mobileNumberError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    // Validation functions remain unchanged
    fun validateName(input: String): String? = if (input.length < 2) "Name must be at least 2 characters long" else null
    fun validateEmail(input: String): String? = if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) "Invalid email address" else null
    fun validateMobileNumber(input: String): String? = if (!input.matches(Regex("^[+]?[0-9]{10,13}$"))) "Invalid mobile number" else null
    fun validatePassword(input: String): String? =
        when {
            input.length < 8 -> "Password must be at least 8 characters long"
            !input.any { it.isDigit() } -> "Password must contain at least one digit"
            !input.any { it.isUpperCase() } -> "Password must contain at least one uppercase letter"
            !input.any { it.isLowerCase() } -> "Password must contain at least one lowercase letter"
            else -> null
        }
    fun validateConfirmPassword(input: String): String? = if (input != password) "Passwords do not match" else null

    // Helper function to validate all fields and return if all are valid
    fun validateAllFields(): Boolean {
        nameError = validateName(name)
        emailError = validateEmail(email)
        mobileNumberError = validateMobileNumber(mobileNumber)
        passwordError = validatePassword(password)
        confirmPasswordError = validateConfirmPassword(confirmPassword)
        return listOf(nameError, emailError, mobileNumberError, passwordError, confirmPasswordError).all { it == null }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image and overlay setup remain unchanged
        Image(
            painter = painterResource(R.drawable.register_screen),
            contentDescription = "Motorcycle Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
        )

        // Registration form UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input fields with error messages
            InputField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                errorMessage = nameError,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
            InputField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                errorMessage = emailError,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
            InputField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = "Mobile Number",
                errorMessage = mobileNumberError,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
            InputField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                errorMessage = passwordError,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                visualTransformation = PasswordVisualTransformation()
            )
            InputField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                errorMessage = confirmPasswordError,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Button click triggers validation
            Button(
                onClick = {
                    if (validateAllFields()) {
                        // Proceed with registration
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Register")
            }
        }
    }
}

// Reusable InputField composable with error handling

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String?,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column {

        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = errorMessage != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}
