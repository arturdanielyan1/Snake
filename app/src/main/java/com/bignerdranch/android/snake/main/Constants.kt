package com.bignerdranch.android.snake.main

import android.content.res.Resources
import android.util.Log
import android.view.ViewGroup
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.snakeStuff.Coordinates
import com.bignerdranch.android.snake.snakeStuff.Snake

const val LOG_TAG = "myLogs"
const val DIALOG_GO = "DialogGameOver"
const val GAME_FRAGMENT = "GameFragment"

// SETTINGS
var stepDelay: Long = 500
var isInMove = false

const val isWaiting = true

var stepDelayDecrement = 3

//BEST SCORE
var bestScore = 0
var currentScore = 0


inline fun log(string: String) = Log.d(LOG_TAG, string)


// FIELD STUFF
var FIELD_WIDTH = 15
var FIELD_HEIGHT = 15

val mapNames = arrayOf("Default", "Box", "Yerevan")

lateinit var selectedMap: ArrayList<ArrayList<Int>>
var selectedMapIndex: Int = 0

val defaultMap: ArrayList<ArrayList<Int>>
    get() = ArrayList<ArrayList<Int>>().apply {
        for (y in 1..FIELD_HEIGHT) add( ArrayList<Int>().apply {
            for(x in 1..FIELD_WIDTH) {
                add(0)
            }
        })
    }

val boxMap: ArrayList<ArrayList<Int>>
    get() = ArrayList<ArrayList<Int>>().apply {
        for (y in 1..FIELD_HEIGHT) add( ArrayList<Int>().apply {
            for(x in 1..FIELD_WIDTH) {
                if(y == 1 || x == 1 || y == FIELD_HEIGHT || x == FIELD_WIDTH) {
                    add(4)
                }else add(0)
            }
        })
    }

val yerevanMap = arrayListOf<ArrayList<Int>>(
    arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
    arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,4,4,4,4,4,0,0,4,4,4,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,4,4,4,4,4,0,0,4,4,4,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,0,0,0,0,4,0,0,4,4,4,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,0,0,0,0,4,0,0,4,4,4,0,0),
    arrayListOf(0,0,4,4,0,0,0,0,0,0,0,0,0,0,0,4,4,4,0,0),
    arrayListOf(0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,0,0),
    arrayListOf(0,0,0,0,0,0,0,0,0,4,4,0,0,0,0,0,4,4,0,0),
    arrayListOf(0,0,0,0,0,0,0,4,4,4,4,4,4,0,0,0,0,4,0,0),
    arrayListOf(0,0,0,0,0,0,4,4,4,4,4,4,4,4,0,0,0,4,0,0),
    arrayListOf(0,0,0,0,0,0,0,4,4,4,4,4,4,0,0,0,0,0,0,0),
    arrayListOf(0,0,4,0,0,0,0,0,0,4,4,0,0,0,0,0,0,0,0,0),
    arrayListOf(0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0),
    arrayListOf(0,0,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,4,4,4,4,4,0,0,0,4,4,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,4,4,4,4,4,0,0,4,4,4,0,0),
    arrayListOf(0,0,4,4,4,0,0,4,4,4,4,4,4,0,0,4,4,4,0,0),
    arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
    arrayListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
)

fun clearField() {
//    selectedMap = (mapIndexToMatrix[selectedMapIndex]!!).cloneMap()
    if(selectedMapIndex == 0) {
        Snake.headPos = Coordinates(FIELD_WIDTH /2, FIELD_HEIGHT /2)
        selectedMap = defaultMap.cloneMap()
        return
    } else if (selectedMapIndex == 1) {
        Snake.headPos = Coordinates(FIELD_WIDTH /2, FIELD_HEIGHT /2)
        selectedMap = boxMap.cloneMap()
        return
    }

    if (selectedMapIndex == 2) {
        selectedMap = yerevanMap.cloneMap()
        Snake.headPos = Coordinates(0,0)
    }
}

val toColor = mapOf(
    0 to R.color.ground,
    1 to R.color.snake_body,
    2 to R.color.food,
    3 to R.drawable.crash,
    4 to R.color.wall
)

val mapIndexToImg = mapOf(
    0 to R.drawable.map_preview_default,
    1 to R.drawable.map_preview_box,
    2 to R.drawable.map_preview_yerevan
)

val mapIndexToMatrix = mapOf(
    0 to ::defaultMap,
    1 to ::boxMap,
    2 to ::yerevanMap
)


fun ArrayList<ArrayList<Int>>.cloneMap(): ArrayList<ArrayList<Int>> {
    val result: ArrayList<ArrayList<Int>> = ArrayList()
    for (i in this.indices) {
        val row = this[i].clone() as ArrayList<Int>
        result.add(row)
    }
    return result
}

// PX DP CONVERSIONS
val Int.toDp
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toPx
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


//TYPE ALIAS
typealias VGLayoutParams = ViewGroup.LayoutParams