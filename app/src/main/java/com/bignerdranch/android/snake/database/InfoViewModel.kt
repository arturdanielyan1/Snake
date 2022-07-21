package com.bignerdranch.android.snake.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoViewModel(private val repository: InfoRepository) : ViewModel() {

    val height: LiveData<Int?> = repository.getHeight()
    val width: LiveData<Int?> = repository.getWidth()
    val mapIndex: LiveData<Int?> = repository.getMapIndex()
    val bestScore: LiveData<Int?> = repository.getBestScore()
    val lastScore: LiveData<Int?> = repository.getLastScore()


    fun changeMapIndex(index: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.changeMapIndex(index)
    }


    fun changeHeight(height: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.changeHeight(height)
    }

    fun changeWidth(width: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.changeWidth(width)
    }

    fun changeBestScore(bestScore: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.changeBestScore(bestScore)
    }

    fun changeLastScore(lastScore: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.changeLastScore(lastScore)
    }

    fun changeInfo(info: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("myLogs","viewModel change info thread ${Thread.currentThread().name}")
        repository.changeInfo(info)
    }


    class InfoViewModelFactory(private val repository: InfoRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InfoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

