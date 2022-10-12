package com.fox.coroutineexample2

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

        Log.d(APP_TAG, "CoroutineExceptionHandler caught exception $throwable")

    }

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob + exceptionHandler)

    fun method() {
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d(APP_TAG, "First coroutine finished")
        }
        val childJob2 = coroutineScope.launch {
            delay(2000)
            Log.d(APP_TAG, "Second coroutine finished")
            launch {
                onError()
            }
        }
        val childJob3 = coroutineScope.launch {
            delay(4000)

            onError()


            Log.d(APP_TAG, "Third coroutine finished")

        }


    }

    private fun onError() {
        throw RuntimeException("Coroutine RuntimeException")
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    companion object {
        private const val APP_TAG = "MainViewModel"
    }

}