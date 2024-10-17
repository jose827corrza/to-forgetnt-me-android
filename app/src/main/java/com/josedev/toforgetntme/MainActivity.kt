package com.josedev.toforgetntme

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.josedev.toforgetntme.components.PermissionDialog
import com.josedev.toforgetntme.permissions.ExactAlarmPermissionTextProvider
import com.josedev.toforgetntme.permissions.PostNotificationsPermissionTextProvider
import com.josedev.toforgetntme.presentation.PermissionViewModel
import com.josedev.toforgetntme.ui.theme.ToForgetntMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = Firebase.auth.currentUser

        FirebaseApp.initializeApp(applicationContext)
        val viewModel by viewModels<PermissionViewModel>()
        val dialogQueue = viewModel.visiblePermissionDialogQueue

//        val loginViewModel: LoginViewModel by viewModels()
        setContent {
            val postNotificationsPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    viewModel.onPermissionResult(
                        permission = Manifest.permission.POST_NOTIFICATIONS,
                        isGranted = isGranted
                    )
                }
            )

            val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    permissions.keys.forEach { permission ->
                        viewModel.onPermissionResult(
                            permission = permission,
                            isGranted = permissions[permission] == true
                        )
                    }
                }
            )

            ToForgetntMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Button(onClick = {
                                postNotificationsPermissionResultLauncher.launch(
                                    Manifest.permission.POST_NOTIFICATIONS
                                )
                            }) {
                                Text(text = "Request one permission")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            multiplePermissionResultLauncher.launch(
                                arrayOf(
                                    Manifest.permission.POST_NOTIFICATIONS,
                                    Manifest.permission.SCHEDULE_EXACT_ALARM
                                )
                            )
                        }) {
                            Text(text = "Request multiple permission")
                        }
                    }

                    dialogQueue.reversed().forEach { permission ->
                        PermissionDialog(
                            permissionsProvider = when (permission) {
                                Manifest.permission.POST_NOTIFICATIONS -> {
                                    PostNotificationsPermissionTextProvider()
                                }

                                Manifest.permission.SCHEDULE_EXACT_ALARM -> {
                                    ExactAlarmPermissionTextProvider()
                                }

                                else -> {
                                    PostNotificationsPermissionTextProvider()

                                }
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(permission),
                            onDismiss = viewModel::dismissDialog,
                            onOkClick = {
                                viewModel.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
//                        AppNavigation()
                    }
                }
            }
        }
    }
}

fun Activity.openAppSettings() {

    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
        .also(::startActivity)
}


//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//    ToForgetntMeTheme {
////        LoginScreen()
//    }
//}