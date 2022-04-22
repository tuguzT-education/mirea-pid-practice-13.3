package io.github.tuguzt.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import io.github.tuguzt.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private val process1Tag = requireNotNull(TextWorker::class.simpleName)
        private val process2Tag = requireNotNull(LongWorker::class.simpleName)
    }

    private lateinit var binding: ActivityMainBinding

    private var count = 0
    private lateinit var data: Data
    private lateinit var process1: OneTimeWorkRequest
    private lateinit var process2: OneTimeWorkRequest

    private val workManager: WorkManager get() = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clicker.setOnClickListener {
            count += 1
            binding.clicker.text = count.toString()
        }

        binding.startProcess.setOnClickListener {
            data = workDataOf("click" to count)
            process1 = OneTimeWorkRequestBuilder<TextWorker>()
                .addTag(process1Tag)
                .build()
            process2 = OneTimeWorkRequestBuilder<LongWorker>()
                .setInputData(data)
                .addTag(process2Tag)
                .build()

            workManager
                .beginWith(process1)
                .then(process2)
                .enqueue()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        workManager.cancelAllWorkByTag(process1Tag)
        workManager.cancelAllWorkByTag(process2Tag)
    }
}
