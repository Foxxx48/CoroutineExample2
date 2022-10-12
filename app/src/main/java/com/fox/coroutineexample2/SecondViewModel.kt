package com.fox.coroutineexample2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SecondViewModel : ViewModel() {

    fun method() {
       val job1 =  viewModelScope.launch(Dispatchers.Default) {
            Log.d(LOG_TAG, "Started job1")
            val before = System.currentTimeMillis()
            var count = 0
            for (i in 0 until 100_000_000) {
                for (j in 0 until 100) {
                    ensureActive()
                    count++
                }
            }
            Log.d(LOG_TAG, "Job1 finished: ${System.currentTimeMillis() - before}")
        }
        job1.invokeOnCompletion {
            Log.d(LOG_TAG, "Job1 was cancelled $it ")
        }

        val job2 = viewModelScope.launch {
            Log.d(LOG_TAG, "Started job2")
            delay(3000)
            job1.cancel()
            Log.d(LOG_TAG, "Job2 finished")
        }
    }


    companion object {
        private const val LOG_TAG = "SecondViewModel"
    }
}