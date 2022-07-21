package com.bignerdranch.android.snake.main

import com.bignerdranch.android.snake.database.InfoViewModel

fun interface SharedViewModel {
    fun getViewModel(): InfoViewModel
}