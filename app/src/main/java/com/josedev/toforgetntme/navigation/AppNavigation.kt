package com.josedev.toforgetntme.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.josedev.toforgetntme.navigation.routes.AppNavigation
import com.josedev.toforgetntme.screens.HomeScreen
import com.josedev.toforgetntme.screens.LoginScreen
import com.josedev.toforgetntme.screens.Splash
import com.josedev.toforgetntme.screens.SplashScreen
import com.josedev.toforgetntme.screens.TodoScreen

@Composable
fun AppNavigation(

) {
    val appNavController = rememberNavController()
    var auth = Firebase.auth.currentUser

    NavHost(navController = appNavController, startDestination = AppNavigation.SplashScreen().route){
        composable(AppNavigation.SplashScreen().route){
            SplashScreen(appNavController, auth)
        }
        composable(AppNavigation.LoginScreen().route){
                LoginScreen(appNavController)
        }
        composable(AppNavigation.TasksScreen().route){
            HomeScreen(appNavController)
        }
        composable(AppNavigation.TodoScreen().route){
            TodoScreen(appNavController, it.arguments?.getString("taskId")!!)
        }
    }
    
}