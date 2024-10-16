package com.josedev.toforgetntme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.josedev.toforgetntme.navigation.AppNavigation
import com.josedev.toforgetntme.presentation.LoginViewModel
import com.josedev.toforgetntme.screens.HomeScreen
import com.josedev.toforgetntme.screens.LoginScreen
import com.josedev.toforgetntme.ui.theme.ToForgetntMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = Firebase.auth.currentUser

        FirebaseApp.initializeApp(applicationContext)
        val loginViewModel: LoginViewModel by viewModels()
        setContent {
            ToForgetntMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        AppNavigation()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ToForgetntMeTheme {
//        LoginScreen()
    }
}