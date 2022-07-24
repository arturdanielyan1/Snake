package com.bignerdranch.android.snake.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.database.InfoApplication
import com.bignerdranch.android.snake.database.InfoViewModel
import com.bignerdranch.android.snake.game.GameFragment
import com.bignerdranch.android.snake.maps.MapsFragment
import com.bignerdranch.android.snake.otherPages.SettingsFragment
import com.bignerdranch.android.snake.otherPages.GameOverFragment
import com.bignerdranch.android.snake.otherPages.MenuFragment

class MainActivity : AppCompatActivity(), FragmentLauncher, SharedViewModel {


    private lateinit var view: FrameLayout

    private val infoViewModel: InfoViewModel by viewModels {
        InfoViewModel.InfoViewModelFactory((application as InfoApplication).repository)
    }

    override fun getViewModel(): InfoViewModel =
        infoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log("MainActivity OnCreate")


        infoViewModel.apply {

            height.observe(this@MainActivity) { height: Int? ->
                log("observe height $height")
                try {
                    FIELD_HEIGHT = height!!
                } catch (npe: NullPointerException) {}
            }

            width.observe(this@MainActivity) { width: Int? ->
                log("observe width $width")
                try {
                    FIELD_WIDTH = width!!
                } catch (npe: NullPointerException) {}
            }

            mapIndex.observe(this@MainActivity) { mapIndex: Int? ->
                log("observe mapIndex $mapIndex")
                try {
                    selectedMapIndex = mapIndex!!
                } catch (npe: NullPointerException) {}
            }

            bestScore.observe(this@MainActivity) { bestScore: Int? ->
                log("observe bestScore $bestScore")
                try {
                    localBestScore = bestScore!!
                } catch (npe: NullPointerException) {}
            }

            mps.observe(this@MainActivity) { mps: Int? ->
                log("observe mps $mps")
                try {
                    stepDelay = 1000 / mps!!.toLong()
                } catch (npe: NullPointerException) {}
            }

            isSpeedingUp.observe(this@MainActivity) { speedingUp: Boolean? ->
                log("observe isSpeedingUp $isSpeedingUp")
                try {
                    if(speedingUp!!) stepDelayDecrement = 0
                    stepDelay = 500
                } catch (npe: NullPointerException) {}
            }

            speedUpMillis.observe(this@MainActivity) { speedUpMillis: Int? ->
                log("observe speedUpMillis $speedUpMillis")
                try {
                    stepDelayDecrement = speedUpMillis!!
                } catch (npe: NullPointerException) {}
            }

        }


        view = findViewById(R.id.fragment_container)
        supportActionBar?.hide()
        log("adding menu fragment")
        addMenuFragment()
    }


    private fun addMenuFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (manager.backStackEntryCount != 0)
        manager.popBackStack(GAME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        transaction.replace(R.id.fragment_container, MenuFragment.newInstance()).commit()
    }


    private fun addGameFragment() {
        log("addGameFragment")
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.addToBackStack(GAME_FRAGMENT)
        transaction.replace(R.id.fragment_container, GameFragment.newInstance(view.width, view.height)).commit()
    }

    private fun addGameOverFragment() {
        val fragment = GameOverFragment.newInstance()
        fragment.show(supportFragmentManager, DIALOG_GO)
    }

    private fun addCustomizeFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, SettingsFragment.newInstance()).commit()
    }

    private fun addMapFragment() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, MapsFragment.newInstance()).commit()
    }

    override fun openMenuFragment() {
        addMenuFragment()
    }

    override fun openGameFragment() {
        addGameFragment()
    }

    override fun openGameOverFragment() {
        addGameOverFragment()
    }

    override fun openCustomizeFragment() {
        addCustomizeFragment()
    }

    override fun openMapsFragment() {
        addMapFragment()
    }
}