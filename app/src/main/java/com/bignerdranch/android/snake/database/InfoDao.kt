package com.bignerdranch.android.snake.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_BEST_SCORE
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_GRAD_SPEED_UP
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_HEIGHT
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_LAST_SCORE
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_MAP_INDEX
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_MPS
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_SPEED_UP_MILLS
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_WIDTH
import com.bignerdranch.android.snake.database.DBSchema.Companion.TABLE_NAME

@Dao
interface InfoDao {


    @Query("SELECT $COL_HEIGHT FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getHeight(): LiveData<Int?>

    @Query("SELECT $COL_WIDTH FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getWidth(): LiveData<Int?>

    @Query("SELECT $COL_MAP_INDEX FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getMapIndex(): LiveData<Int?>

    @Query("SELECT $COL_BEST_SCORE FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getBestScore(): LiveData<Int?>

    @Query("SELECT $COL_LAST_SCORE FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getLastScore(): LiveData<Int?>


    //DB V2 ////////////////////////////////////////////////////////////////////

    @Query("SELECT $COL_MPS FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getMPS(): LiveData<Int?>

    @Query("SELECT $COL_GRAD_SPEED_UP FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun isSpeedingUp(): LiveData<Boolean?>

    @Query("SELECT $COL_SPEED_UP_MILLS FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getSpeedUpMillis(): LiveData<Int?>

    //////////////////////////////////////////////////////////////////////////////////

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    fun getAllInfo(): Entity?

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(info: Entity)
}