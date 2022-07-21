package com.bignerdranch.android.snake.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_LAST_SCORE
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_BEST_SCORE
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_HEIGHT
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_MAP_INDEX
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_WIDTH
import com.bignerdranch.android.snake.database.DBSchema.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Entity(@PrimaryKey(autoGenerate = true) val id: Int,
                  @ColumnInfo(name = COL_MAP_INDEX) val mapIndex: Int,
                  @ColumnInfo(name = COL_HEIGHT) val height: Int,
                  @ColumnInfo(name = COL_WIDTH) val width: Int,
                  @ColumnInfo(name = COL_BEST_SCORE) val bestScore: Int,
                  @ColumnInfo(name = COL_LAST_SCORE) val lastScore: Int,
)