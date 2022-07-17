package com.bignerdranch.android.snake.snakeStuff

import com.bignerdranch.android.snake.game.GameFragment
import com.bignerdranch.android.snake.main.*


object Snake {
    var length: Int = 2

    var trace: MutableList<Coordinates> = mutableListOf()

    var direction: Direction = Direction.RIGHT
        set(value) {
            if(value.key != -direction.key) field = value
        }

    var headPos: Coordinates = Coordinates(FIELD_WIDTH /2, FIELD_HEIGHT /2)

    fun move() {
        isInMove = true

        trace.add(Coordinates(headPos.x, headPos.y))


        if ((headPos.x == FIELD_WIDTH -1 || headPos.x == 0 || headPos.y == FIELD_HEIGHT -1 || headPos.y == 0) && mapIndexToMatrix[selectedMapIndex]!!.get()[headPos.y][headPos.x] == 0){
            if(headPos.x == FIELD_WIDTH -1 && direction == Direction.RIGHT) {
                headPos = Coordinates(0, headPos.y)

                commitMove()
                return
            } else if(headPos.x == 0 && direction == Direction.LEFT) {
                headPos = Coordinates(FIELD_WIDTH -1, headPos.y)

                commitMove()
                return
            } else if(headPos.y == FIELD_HEIGHT -1 && direction == Direction.DOWN) {
                headPos = Coordinates(headPos.x, 0)

                commitMove()
                return
            } else if(headPos.y == 0 && direction == Direction.UP) {
                headPos = Coordinates(headPos.x, FIELD_HEIGHT -1)

                commitMove()
                return
            }
        }
        when(direction) {
            Direction.RIGHT -> {
                headPos.x++
            }
            Direction.LEFT -> {
                headPos.x--
            }
            Direction.UP -> {
                headPos.y--
            }
            Direction.DOWN -> {
                headPos.y++
            }
        }

        commitMove()
    }

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun commitMove() {
        if(headPos == Food.position) {
            length++
            if(stepDelay > 100) {
                stepDelay -= stepDelayDecrement
            }else stepDelay = 100
            currentScore++
            Food.position = Food.getRandomCoordinates()
        }


        isInMove = false
        synchronized(isInMove) {
            (isInMove as Object).notifyAll()
        }

        // lose
        if((trace.size >= length && trace.subList(trace.size-length, trace.size).contains(headPos)) || selectedMap[headPos.y][headPos.x] == 4) {
            selectedMap[headPos.y][headPos.x] = 3
//            if (GameFragment.gameRunner?.state != Thread.State.TIMED_WAITING)
//            synchronized(isWaiting) {
//                (isWaiting as Object).wait()
//            }
            GameFragment.gameRunner?.interrupt()
            GameFragment.lost = true
            return
        }

        selectedMap[headPos.y][headPos.x] = 1
    }

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    fun reinit() {
        direction = Direction.RIGHT
        length = 2
        trace.clear()
        clearField()
    }
}