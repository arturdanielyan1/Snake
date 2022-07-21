package com.bignerdranch.android.snake.game

import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.snake.main.isWaiting
import com.bignerdranch.android.snake.main.log
import com.bignerdranch.android.snake.main.stepDelay
import com.bignerdranch.android.snake.snakeStuff.CleanerTail
import com.bignerdranch.android.snake.snakeStuff.Food
import com.bignerdranch.android.snake.snakeStuff.Snake

class GameRunner(private val activity: AppCompatActivity,
                 private val render: () -> Unit,
                 private val updateScore: () -> Unit,
                 private val updateBestScore: () -> Unit) : Runnable {

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    override fun run() {
        Snake.reinit()
        Food.position = Food.getRandomCoordinates()
        while (true) {
            Snake.move()
            CleanerTail.clear()
            log("gameRunner")
            activity.runOnUiThread(render)
            activity.runOnUiThread(updateScore)
            activity.runOnUiThread(updateBestScore)
            try {
                Thread.sleep(stepDelay)
            } catch (e: InterruptedException) {
                break
            }
            synchronized(isWaiting){
                (isWaiting as Object).notifyAll()
            }
        }
    }
}