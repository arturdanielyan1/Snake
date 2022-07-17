package com.bignerdranch.android.snake.snakeStuff

import com.bignerdranch.android.snake.main.FIELD_HEIGHT
import com.bignerdranch.android.snake.main.FIELD_WIDTH
import com.bignerdranch.android.snake.main.selectedMap
import com.bignerdranch.android.snake.snakeStuff.Snake.length
import com.bignerdranch.android.snake.snakeStuff.Snake.trace

object Food {
    var position: Coordinates = getRandomCoordinates()
        set(value) {
            field = value
            selectedMap[position.y][position.x] = 2
        }


    tailrec fun getRandomCoordinates(): Coordinates {
        val randomInt = {min: Int, max: Int -> (Math.random()*(max-min) + min).toInt()}// returns an int in range [min, max)
        val coordinates = Coordinates(randomInt(0, FIELD_WIDTH),randomInt(0, FIELD_HEIGHT))

//        (trace.size >= length && trace.subList(trace.size-length, trace.size).contains(Snake.headPos))
        val sublistStartIndex = if(trace.size-length-3<0){
            0
        }else trace.size-length-2

        return if(selectedMap[coordinates.y][coordinates.x] == 1 || selectedMap[coordinates.y][coordinates.x] == 4){
            getRandomCoordinates()//41 19
        }else coordinates
    }
}