package com.josedev.toforgetntme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.josedev.toforgetntme.navigation.routes.AppNavigation
import com.josedev.toforgetntme.ui.theme.ToForgetntMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    nav: NavController
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")})
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = password,
//            trailingIcon = { Icon(imageVector = Icons.Default.Warning, contentDescription = "Check")},
            onValueChange = {password = it},
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(40.dp))
        FilledTonalButton(onClick = { logIn(email, password, nav) }, modifier = Modifier.width(200.dp)) {
            Text(text = "Log in")
        }
        ElevatedButton(onClick = { signUp(email, password, nav) }, modifier = Modifier.width(200.dp)) {
            Text(text = "Sign up")
        }
    }
}

fun logIn(email: String, password: String, nav: NavController) {
    nav.navigate(AppNavigation.TasksScreen().route)
}

fun signUp(email: String, password: String, nav: NavController) {}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ToForgetntMeTheme {
//        LoginScreen()
    }
}