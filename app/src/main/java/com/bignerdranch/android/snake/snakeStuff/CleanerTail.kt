package com.bignerdranch.android.snake.snakeStuff

import com.bignerdranch.android.snake.main.selectedMap
import com.bignerdranch.android.snake.snakeStuff.Snake.length
import com.bignerdranch.android.snake.snakeStuff.Snake.trace

object CleanerTail {
    var position: Coordinates = Coordinates(0,0)

    fun clear() {
        if(trace.size > length) {
            position = Snake.trace[trace.size-1-Snake.length]
            if(position != Snake.headPos)
            selectedMap[position.y][position.x] = 0

            if (position == Food.position)
                selectedMap[position.y][position.x] = 2
        }
    }
}