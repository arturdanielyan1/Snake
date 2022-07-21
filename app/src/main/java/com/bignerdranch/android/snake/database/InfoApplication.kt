package com.bignerdranch.android.snake.database

import android.app.Application
import com.bignerdranch.android.snake.main.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class InfoApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { InfoDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { InfoRepository(database.infoDao(), applicationScope) }

}