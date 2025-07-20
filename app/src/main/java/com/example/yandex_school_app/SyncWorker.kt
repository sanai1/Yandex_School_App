package com.example.yandex_school_app

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.yandex_school_app.di.AppMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private val synchronizedCustom by lazy {
        (applicationContext as AppMain).appComponent.synchronizedCustom()
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            synchronizedCustom.sync()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}