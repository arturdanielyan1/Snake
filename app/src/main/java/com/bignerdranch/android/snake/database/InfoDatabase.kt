package com.bignerdranch.android.snake.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_GRAD_SPEED_UP
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_MPS
import com.bignerdranch.android.snake.database.DBSchema.Companion.COL_SPEED_UP_MILLS
import com.bignerdranch.android.snake.database.DBSchema.Companion.TABLE_NAME
import com.bignerdranch.android.snake.database.DBSchema.Companion.VERSION
import com.bignerdranch.android.snake.main.log
import com.bignerdranch.android.snake.main.stepDelay
import com.bignerdranch.android.snake.main.stepDelayDecrement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Entity::class], version = VERSION)
abstract class InfoDatabase : RoomDatabase() {

    abstract fun infoDao(): InfoDao

    companion object {
        @Volatile
        private var INSTANCE: InfoDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): InfoDatabase =
            INSTANCE ?: synchronized(this) {
                log("getDatabase")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InfoDatabase::class.java,
                    "info_database"
                ).addCallback(InfoDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
    }

    private class InfoDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.infoDao().deleteAll()
                    database.infoDao().insert(Entity(0, 0, 15, 15, 0, 0, 2, false, 10))
                }
            }
        }
    }
}