package com.bignerdranch.android.snake.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.snake.database.DBSchema.Companion.VERSION
import com.bignerdranch.android.snake.main.log
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
                    log("prepopulating db")
                    database.infoDao().deleteAll()
                    database.infoDao().insert(Entity(0, 0, 15, 15, 0, 0))
                }
            }
        }
    }
}