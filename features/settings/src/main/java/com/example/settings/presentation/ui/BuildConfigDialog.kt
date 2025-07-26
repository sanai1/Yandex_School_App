package com.example.settings.presentation.ui

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BuildConfigDialog(
    dialogState: MutableState<Boolean>
) {
    val context = LocalContext.current
    val packageInfo = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                context.packageName, PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
    } catch (_: PackageManager.NameNotFoundException) {
        null
    }
    val versionName = packageInfo?.versionName ?: try {
        Class.forName("${context.packageName}.BuildConfig").getField("VERSION_NAME")
            .get(null) as? String
    } catch (_: Exception) {
        "Unknows"
    } ?: "Unknows"
    val versionCode = packageInfo?.versionCode?.toString() ?: try {
        val buildConfigClass = Class.forName("${context.packageName}.BuildConfig")
        buildConfigClass.getField("VERSION_CODE").get(null)?.toString()
    } catch (_: Exception) {
        "Unknown"
    } ?: "Unknown"
    val lastUpdateTime = packageInfo?.lastUpdateTime ?: 0L
    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        title = { Text("Информация о приложении") },
        text = {
            Column {
                Text("Version name: $versionName")
                Text("Version code: $versionCode")
                Text(
                    "Last update: ${
                        if (lastUpdateTime > 1) SimpleDateFormat(
                            "dd MMMM yyyy, HH:mm",
                            Locale.getDefault()
                        )
                            .format(Date(lastUpdateTime)) else "Unknow"
                    }"
                )
            }
        },
        confirmButton = {}
    )
}