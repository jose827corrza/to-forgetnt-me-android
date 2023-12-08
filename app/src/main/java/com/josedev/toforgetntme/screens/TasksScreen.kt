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
import com.josedev.toforgetntme.components.CardComponent
import com.josedev.toforgetntme.domain.entity.ToDo

@Composable
fun TasksScreen(
    tasksList: List<ToDo>
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(0.dp, 10.dp),
    ) {
        LazyColumn{
            items(tasksList){
                CardComponent()
            }
        }
    }
}