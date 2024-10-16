package com.josedev.toforgetntme.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.josedev.toforgetntme.navigation.routes.AppNavigation
import com.josedev.toforgetntme.presentation.HomeViewModel
import com.josedev.toforgetntme.presentation.LoginViewModel
import com.josedev.toforgetntme.repository.HomeEvent
import com.josedev.toforgetntme.repository.LoginEvent
import com.josedev.toforgetntme.ui.theme.ToForgetntMeTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    nav: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {

    val loginState by loginViewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(true)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        OutlinedTextField(
            singleLine = true,
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")},
            keyboardOptions = KeyboardOptions(autoCorrect = false, keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            singleLine = true,
            value = password,
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = if(isPasswordVisible) Icons.Default.Lock else Icons.Default.Warning, contentDescription = "Check")}

                },
            onValueChange = {password = it},
            label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            visualTransformation = if(isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None)
        Spacer(modifier = Modifier.height(40.dp))
        FilledTonalButton(onClick = {
            scope.launch {
                loginViewModel.onEvent(LoginEvent.Login(email, password))
                delay(1200L)
                if(loginViewModel.state.value.user != null){
                    nav.navigate(AppNavigation.TasksScreen().route)
                }else if(loginState.isError) {
                    Toast.makeText(context, "Check your credentials", Toast.LENGTH_LONG).show()
                }
            }

                                    },
            modifier = Modifier.width(200.dp))
        {
            Text(text = "Log in")
        }
        ElevatedButton(onClick = {
            scope.launch {
                loginViewModel.onEvent(LoginEvent.SignUp(email, password))
                delay(1200L)
                if(loginState.user != null){
                    nav.navigate(AppNavigation.TasksScreen().route)
                }else {
                    Toast.makeText(context, "Error signing up", Toast.LENGTH_LONG).show()
                }

            }
                                 },
            modifier = Modifier.width(200.dp))
        {
            Text(text = "Sign up")
        }
    }
    if(loginState.isError) {
        Toast.makeText(context, "Check your credentials.", Toast.LENGTH_LONG).show()
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ToForgetntMeTheme {
//        LoginScreen()
    }
}