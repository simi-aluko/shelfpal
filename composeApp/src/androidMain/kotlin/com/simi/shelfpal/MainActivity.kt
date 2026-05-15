package com.simi.shelfpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.permission.permissionUtil
import com.simi.shelfpal.notification.AndroidBackgroundScheduler
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val permissionUtil by permissionUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
        permissionUtil.askNotificationPermission()
        AndroidBackgroundScheduler(this).startScheduler()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
