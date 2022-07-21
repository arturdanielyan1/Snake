package com.bignerdranch.android.snake.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.bignerdranch.android.snake.main.log
import kotlinx.coroutines.CoroutineScope

class InfoRepository(private val infoDao: InfoDao, private val scope: CoroutineScope) {

    private val allInfo: Entity?
        get() = infoDao.getAllInfo()

    fun getHeight(): LiveData<Int?> =
        infoDao.getHeight()

    fun getWidth(): LiveData<Int?> =
        infoDao.getWidth()

    fun getMapIndex(): LiveData<Int?> =
        infoDao.getMapIndex()

    fun getBestScore(): LiveData<Int?> =
        infoDao.getBestScore()

    fun getLastScore(): LiveData<Int?> =
        infoDao.getLastScore()


    @WorkerThread
    @Synchronized fun changeMapIndex(index: Int) {
//        scope.launch {
            log("changeMapIndex " + allInfo?.toString())
            val newInfo = allInfo?.copy(mapIndex = index)
            log("changeMapIndex " + newInfo.toString())
            infoDao.deleteAll()
            infoDao.insert(newInfo!!)
//        }
    }

    @WorkerThread
    @Synchronized fun changeHeight(height: Int) {
//        scope.launch {
            log("changeHeight " + allInfo?.toString())
            val newInfo = allInfo?.copy(height = height)
            log("changeHeight " + newInfo.toString())
            infoDao.deleteAll()
            infoDao.insert(newInfo!!)
//        }
    }

    @WorkerThread
    @Synchronized fun changeWidth(width: Int) {
//        scope.launch {
            log("changeWidth " + allInfo?.toString())
            val newInfo = allInfo?.copy(width = width)
            log("changeWidth " + newInfo.toString())
            infoDao.deleteAll()
            infoDao.insert(newInfo!!)
//        }
    }

    @WorkerThread
    @Synchronized fun changeBestScore(bestScore: Int) {
//        scope.launch {
            log("changeBestScore " + allInfo?.toString())
            val newInfo = allInfo?.copy(bestScore = bestScore)
            log("changeBestScore " + newInfo.toString())
            infoDao.deleteAll()
            infoDao.insert(newInfo!!)
//        }
    }

    @WorkerThread
    @Synchronized fun changeLastScore(lastScore: Int) {
//        scope.launch {
            log("changeLastScore" + allInfo?.toString())
            val newInfo = allInfo?.copy(lastScore = lastScore)
            log("changeLastScore" + newInfo.toString())
            infoDao.deleteAll()
            infoDao.insert(newInfo!!)
//        }
    }

    @WorkerThread
    fun changeInfo(info: Entity) {
        infoDao.deleteAll()
        infoDao.insert(info)
    }
}