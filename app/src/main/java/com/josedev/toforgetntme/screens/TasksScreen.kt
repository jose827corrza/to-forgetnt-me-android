package com.josedev.toforgetntme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.josedev.toforgetntme.components.CardComponent
import com.josedev.toforgetntme.domain.entity.ToDo

@Composable
fun TasksScreen(
    tasksList: List<ToDo>,
    nav: NavController,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(5f.dp, 10.dp),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            items(tasksList){
                CardComponent(nav, it)
            }
        }
    }
}