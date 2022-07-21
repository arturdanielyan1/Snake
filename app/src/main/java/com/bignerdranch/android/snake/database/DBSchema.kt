package com.bignerdranch.android.snake.database

class DBSchema {
    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "dataBase"
        const val TABLE_NAME = "data"

        const val COL_MAP_INDEX = "lastSelectedMapIndex"
        const val COL_HEIGHT = "lastPutHeight"
        const val COL_WIDTH = "lastPutWidth"
        const val COL_BEST_SCORE = "bestScore"
        const val COL_LAST_SCORE = "lastScore"
    }
}