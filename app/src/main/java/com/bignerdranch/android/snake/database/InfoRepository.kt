package com.bignerdranch.android.snake.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.bignerdranch.android.snake.main.log

class InfoRepository(private val infoDao: InfoDao) {

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

    //DB V2
    fun getMPS(): LiveData<Int?> =
        infoDao.getMPS()

    fun isSpeedingUp(): LiveData<Boolean?> =
        infoDao.isSpeedingUp()

    fun getSpeedUpMillis(): LiveData<Int?> =
        infoDao.getSpeedUpMillis()


    @WorkerThread
    @Synchronized fun changeMapIndex(index: Int) {
        log("changeMapIndex " + allInfo?.toString())
        val newInfo = allInfo?.copy(mapIndex = index)
        log("changeMapIndex " + newInfo.toString())
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeHeight(height: Int) {
        log("changeHeight " + allInfo?.toString())
        val newInfo = allInfo?.copy(height = height)
        log("changeHeight " + newInfo.toString())
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeWidth(width: Int) {
        log("changeWidth " + allInfo?.toString())
        val newInfo = allInfo?.copy(width = width)
        log("changeWidth " + newInfo.toString())
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeBestScore(bestScore: Int) {
        log("changeBestScore " + allInfo?.toString())
        val newInfo = allInfo?.copy(bestScore = bestScore)
        log("changeBestScore " + newInfo.toString())
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeLastScore(lastScore: Int) {
        log("changeLastScore" + allInfo?.toString())
        val newInfo = allInfo?.copy(lastScore = lastScore)
        log("changeLastScore" + newInfo.toString())
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeMPS(mps: Int) {
        val newInfo = allInfo?.copy(movesPerSecond = mps)
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeIsSpeedingUp(isSpeedingUp: Boolean) {
        val newInfo = allInfo?.copy(gradSpeedUp = isSpeedingUp)
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }

    @WorkerThread
    @Synchronized fun changeSpeedUpMillis(speedUpMillis: Int) {
        val newInfo = allInfo?.copy(gradSpeedUpMillis = speedUpMillis)
        infoDao.deleteAll()
        infoDao.insert(newInfo!!)
    }
}