package com.josedev.toforgetntme.screens

import androidx.compose.foundation.layout.Column
import com.josedev.toforgetntme.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true){
        delay(600)
        navController.popBackStack() // Prevents to be able to return to splash screen
//        navController.navigate(AppScreens.AuthScreen.route)
    }
    Splash()
}
@Composable
fun Splash () {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.josedevlogo),
            contentDescription = "josedev logo",
            Modifier.size(150.dp, 150.dp)
        )
        Text(
            text = "To Forgetn't me",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}