package com.josedev.toforgetntme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.josedev.toforgetntme.presentation.HomeViewModel
import com.josedev.toforgetntme.repository.HomeEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    nav: NavController,
    taskId: String,
    homeVM: HomeViewModel = hiltViewModel()
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var isComplete by remember {
        mutableStateOf(false)
    }
    val datePickerDialogState = rememberMaterialDialogState()
    val timePickerDialogState = rememberMaterialDialogState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            text = if(taskId != "new") "Update Todo" else "Create Todo",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            label = { Text(text = "Todo Title")},
            value = title,
            onValueChange = {title = it})

        OutlinedTextField(
            label = { Text(text = "Todo Description")},
            value = description,
            onValueChange = {description = it})
        Row (
            modifier = Modifier
                .fillMaxWidth(0.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Switch(
                checked = isComplete,
                onCheckedChange = { isComplete = it},
                thumbContent = { if(isComplete){
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Task is complete")
                } }
                )
            Text(text = "Task Finished?")
        }

        Row (
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            FilledTonalButton(onClick = { datePickerDialogState.show() }, modifier = Modifier.width(130.dp)) {
                Text(text = "Pick Date")
            }
            FilledTonalButton(onClick = { timePickerDialogState.show() }, modifier = Modifier.width(130.dp)) {
                Text(text = "Pick Time")
            }
        }
        ElevatedButton(onClick = {
            homeVM.onEvent(HomeEvent.getTaskById(taskId))
        }) {
            Text(text = "Create Todo")
        }
    }
    MaterialDialog (
        dialogState = datePickerDialogState,
        buttons = {
            positiveButton(text = "OK")
        }
    ){
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a Date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
        ){
            // TODO
        }
    }

    MaterialDialog (
        dialogState = timePickerDialogState,
        buttons = {
            positiveButton(text = "OK")
        }
    ){
        timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a Date",
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
        ){
            // TODO
        }
    }
}




@Preview(showBackground = true)
@Composable
fun TodoScreenPreview() {
//    TodoScreen()
}