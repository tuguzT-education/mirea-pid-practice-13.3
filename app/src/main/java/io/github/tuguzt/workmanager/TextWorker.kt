package io.github.tuguzt.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class TextWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        private const val LOG_TAG = "test_worker"

        private val letters = 'a'..'z'
        private const val wordToFound = "friend"
    }

    override fun doWork(): Result {
        Log.d(LOG_TAG, "text_worker_start")

        for (i in letters) {
            for (j in letters) {
                for (k in letters) {
                    for (p in letters) {
                        for (t in letters) {
                            for (m in letters) {
                                val s = buildString { append(i, j, k, p, t, m) }
                                if (s == wordToFound) {
                                    Log.d(LOG_TAG, "text_worker_stop")
                                    val outputData = Data.Builder()
                                        .putAll(inputData)
                                        .putString("data_is", s)
                                        .build()
                                    return Result.success(outputData)
                                }
                            }
                        }
                    }
                }
            }
        }
        return Result.failure()
    }
}
