package com.josedev.toforgetntme.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.josedev.toforgetntme.domain.dto.ToDoDTO
import com.josedev.toforgetntme.domain.entity.ToDo
import com.josedev.toforgetntme.navigation.routes.AppNavigation
import com.josedev.toforgetntme.presentation.HomeViewModel
import com.josedev.toforgetntme.presentation.TaskViewModel
import com.josedev.toforgetntme.repository.HomeEvent
import com.josedev.toforgetntme.repository.TaskEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    nav: NavController,
    taskId: String,
    taskVM: TaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by taskVM.state.collectAsState()

    var title by remember(state.taskData.title) {
        mutableStateOf(state.taskData.title)
    }
    var description by remember(state.taskData.description) {
        mutableStateOf(state.taskData.description)
    }
    var isComplete by remember(state.taskData.isDone) {
        mutableStateOf(state.taskData.isDone)
    }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("yyyy-MM-dd").format(pickedDate)
//            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
//            DateTimeFormatter.ofPattern("kk:mm").format(pickedTime)
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(pickedTime)
        }
    }
    val datePickerDialogState = rememberMaterialDialogState()
    val timePickerDialogState = rememberMaterialDialogState()


    if(state.isLoading){
        taskVM.onEvent(TaskEvent.GetTaskById(taskId))
//        if(taskId != "new"){
//            taskVM.onEvent(TaskEvent.GetTaskById(taskId))
//        }
        val time = LocalTime.parse("19:34:50.63")
        val t = time.getLong(ChronoField.MILLI_OF_SECOND)

       Column(
           modifier = Modifier.fillMaxSize(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally) {
           CircularProgressIndicator(
               modifier = Modifier
                   .width(64.dp)
                   .height(64.dp),
               color = MaterialTheme.colorScheme.secondary,
           )
       }
    } else {
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
            Row (horizontalArrangement = Arrangement.Center){
                ElevatedButton(onClick = {
                    if(taskId == "new"){
                        Log.d("TScreen", "WANT FORMAT: yyyy-m[m]-d[d] hh:mm:ss[.f…]")
                        Log.d("TScreen", "pickedDate: $pickedDate")
                        Log.d("TScreen", "pickedTime: $pickedTime")
                        Log.d("TScreen", "------")
                        Log.d("TScreen", "formattedDate: $formattedDate")
                        Log.d("TScreen", "formattedTime: $formattedTime")
                        taskVM.onEvent(TaskEvent.CreateNewTask(ToDoDTO(title,description,isComplete, 0L,formattedDate, formattedTime, pickedDate, pickedTime), context))
                        nav.navigate(AppNavigation.TasksScreen().route)
                    }else {
                        taskVM.onEvent(TaskEvent.UpdateTask(taskId, ToDoDTO(title,description,isComplete, 0L,formattedDate, formattedTime, pickedDate, pickedTime), context))
                        nav.navigate(AppNavigation.TasksScreen().route)
                    }
                }) {
                    Text(text = if(taskId == "new") "Create Todo" else "Update Todo")
                }
            }
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
            pickedDate = it
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
            title = "Pick a Time",
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
        ){
            pickedTime = it
        }
    }
}




@Preview(showBackground = true)
@Composable
fun TodoScreenPreview() {
//    TodoScreen()
}