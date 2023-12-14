package com.josedev.toforgetntme.screens

import android.util.Log
import android.widget.Spinner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.navigation.routes.AppNavigation
import com.josedev.toforgetntme.presentation.HomeViewModel
import com.josedev.toforgetntme.presentation.LoginViewModel
import com.josedev.toforgetntme.repository.HomeEvent
import com.josedev.toforgetntme.repository.LoginEvent
import com.josedev.toforgetntme.ui.theme.ToForgetntMeTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    nav: NavController,
    loginVM: LoginViewModel = hiltViewModel(),
    homeVM: HomeViewModel = hiltViewModel()
) {
    val testTaskList = arrayListOf<ToDo>()
    val state by homeVM.state.collectAsState()
//    testTaskList.add(ToDo("testTitle","descrip", body = "", topics = emptyList<String>(),))
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text ="Forgetn't Menu", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Tasks") },
                    selected = false,
                    onClick = {
                        scope.launch {
                                drawerState.close()
                        }
                    })
                NavigationDrawerItem(
                    label = { Text(
                        text = "Posts   (Soon)",
                        color = Color.Gray,
                        fontWeight = FontWeight.Light) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    })
                NavigationDrawerItem(
                    label = { Text(
                        text = "Sign Out",
                        fontWeight = FontWeight.Bold) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            loginVM.onEvent(LoginEvent.SignOut)
                            nav.navigate(AppNavigation.LoginScreen().route)
                        }
                    })
            }


        }
    ){
        Scaffold (
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    title = { Text(text = "To Forgetn't me") },
                    actions = {
                        IconButton(
                            onClick = { scope.launch {
                                drawerState.open()
                                    }
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
//                    nav.navigate(AppNavigation.TodoScreen.route)
                    nav.navigate(AppNavigation.UpsertTodoScreen("new").route)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {innerPadding ->
            Box (modifier = Modifier.padding(innerPadding)){
                if (state.isLoading){
                    homeVM.onEvent(HomeEvent.getAlltasks)
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(64.dp)
                                .height(64.dp),
                            color = MaterialTheme.colorScheme.secondary,

                        )

                    }
                }else{
                    TasksScreen(state.taskList, nav)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ToForgetntMeTheme {
//        HomeScreen()
    }
}