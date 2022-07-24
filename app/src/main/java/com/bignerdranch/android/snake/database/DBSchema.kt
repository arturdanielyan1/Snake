package com.bignerdranch.android.snake.database

class DBSchema {
    companion object {
        const val VERSION = 1
        const val TABLE_NAME = "data"

        const val COL_MAP_INDEX = "lastSelectedMapIndex"
        const val COL_HEIGHT = "lastPutHeight"
        const val COL_WIDTH = "lastPutWidth"
        const val COL_BEST_SCORE = "bestScore"
        const val COL_LAST_SCORE = "lastScore"
        //DB V2
        const val COL_MPS = "movesPerSecond"
        const val COL_GRAD_SPEED_UP = "graduallySpeedUp" //boolean
        const val COL_SPEED_UP_MILLS = "graduallySpeedUpMillisecondsPerFood"
    }
}