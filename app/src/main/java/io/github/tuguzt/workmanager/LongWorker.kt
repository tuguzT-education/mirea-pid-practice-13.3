package io.github.tuguzt.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LongWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val LOG_TAG = "test_worker"
    }

    override suspend fun doWork(): Result {
        Log.d(LOG_TAG, "long_worker_start")

        val data1 = inputData.getString("data_is") ?: "none"
        val data2 = inputData.getInt("click", 100)
        val range1 = 1..data1.length * 10_000
        val range2 = 1..data2

        var p: Long = 0
        for (i in range1) {
            for (j in range2) {
                p += i % j
            }
        }

        Log.d(LOG_TAG, "long_worker_stop with result $p")
        return Result.success()
    }
}
