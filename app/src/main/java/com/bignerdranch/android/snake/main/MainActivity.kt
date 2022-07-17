package com.bignerdranch.android.snake.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.snake.*
import com.bignerdranch.android.snake.game.GameFragment
import com.bignerdranch.android.snake.maps.MapsFragment
import com.bignerdranch.android.snake.otherPages.CustomizeFragment
import com.bignerdranch.android.snake.otherPages.GameOverFragment
import com.bignerdranch.android.snake.otherPages.MenuFragment

class MainActivity : AppCompatActivity(), FragmentLauncher {

    companion object {
        @JvmStatic var screenHeight = 0
        @JvmStatic var screenWidth = 0
    }

    private lateinit var view: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log("MainActivity OnCreate")
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
        transaction.replace(R.id.fragment_container, CustomizeFragment.newInstance()).commit()
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