package com.josedev.toforgetntme.navigation.routes

sealed class AppNavigation(val route: String){
    class SplashScreen: AppNavigation("splash_screen")
    class LoginScreen: AppNavigation("login")
    class TasksScreen: AppNavigation("tasks")

    class TodoScreen: AppNavigation("todo/{taskId}")
    class UpsertTodoScreen(string: String): AppNavigation("todo/$string")
}
