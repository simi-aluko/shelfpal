package com.simi.shelfpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.mmk.kmpnotifier.permission.permissionUtil
import com.simi.shelfpal.work.expiryNotificationWorkRequest

class MainActivity : ComponentActivity() {

    val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }

        permissionUtil.askNotificationPermission()

        WorkManager.getInstance(this).apply {
            enqueueUniquePeriodicWork(
                uniqueWorkName = "expiry_updates",
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
                expiryNotificationWorkRequest
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
