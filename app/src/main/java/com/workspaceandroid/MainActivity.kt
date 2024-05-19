package com.workspaceandroid

import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.workspaceandroid.navigation.navGraph.RootNavGraph
import com.workspaceandroid.notification.RemindersManager
import com.workspaceandroid.ui.theme.WorkspaceAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkspaceAndroidTheme {
                RootNavGraph(navController = rememberNavController())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkspaceAndroidTheme {
        MainScreen()
    }
}