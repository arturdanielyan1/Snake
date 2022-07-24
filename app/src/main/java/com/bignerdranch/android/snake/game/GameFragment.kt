package com.bignerdranch.android.snake.game

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.database.InfoViewModel
import com.bignerdranch.android.snake.main.*
import com.bignerdranch.android.snake.snakeStuff.Direction
import com.bignerdranch.android.snake.snakeStuff.Snake


class GameFragment : Fragment(), View.OnClickListener {

    companion object {
        @JvmStatic private var fragmentLauncher: FragmentLauncher? = null

        @JvmStatic var screenWidth: Int = 0
        @JvmStatic var screenHeight: Int = 0
        @JvmStatic var lost = false//changes only in one place and never checked its value but change is handled in the setter
            set(value) {
                if(value){
                    fragmentLauncher?.openGameOverFragment()
                    field = false
                }
            }

        @JvmStatic
        fun newInstance(width: Int, height: Int): GameFragment {
            screenWidth = width
            screenHeight = height
            return GameFragment()
        }

        @JvmStatic
        var gameRunner: Thread? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentLauncher = activity as FragmentLauncher
        viewModel = (activity as SharedViewModel).getViewModel()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentLauncher = null
    }


    private lateinit var gameLinear: LinearLayoutCompat
    private lateinit var scoreTv: TextView
    private lateinit var bestScoreTv: TextView

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        currentScore = 0
        if(selectedMapIndex == 0 || selectedMapIndex == 1) {
            viewModel.height.observe(viewLifecycleOwner) { height: Int? ->
                FIELD_HEIGHT = height!!
            }
            viewModel.width.observe(viewLifecycleOwner) { width: Int? ->
                FIELD_WIDTH = width!!
            }
        } else {
            FIELD_WIDTH = mapIndexToMatrix[selectedMapIndex]!!.get()[0].size
            FIELD_HEIGHT = mapIndexToMatrix[selectedMapIndex]!!.get().size
        }

        log("gameFragment onnCreateView")
        log(screenHeight.toString())

//        val screenWidth = resources.displayMetrics.widthPixels
//        val screenHeight = resources.displayMetrics.heightPixels
        val initialSquareSide = screenWidth / FIELD_WIDTH
        val buttonBoxHeight = screenHeight - initialSquareSide * FIELD_HEIGHT
        val squareSide = if (buttonBoxHeight < (280+60).toPx){
            (screenHeight - (280+60).toPx) / FIELD_HEIGHT
        } else initialSquareSide
        val gameLinearWidth = squareSide * FIELD_WIDTH
        val gameLinearHeight = squareSide * FIELD_HEIGHT

        val mainLinear = LinearLayoutCompat(activity as Context)
        mainLinear.orientation = LinearLayoutCompat.VERTICAL
        val paramsMainLinear = VGLayoutParams(VGLayoutParams.MATCH_PARENT, VGLayoutParams.MATCH_PARENT)
        mainLinear.layoutParams = paramsMainLinear

        gameLinear = LinearLayoutCompat(activity as Context)
        gameLinear.orientation = LinearLayoutCompat.VERTICAL
        val paramsGameLinear = LinearLayoutCompat.LayoutParams(gameLinearWidth, gameLinearHeight)
        paramsGameLinear.weight = 1f
        gameLinear.layoutParams = paramsGameLinear
        paramsGameLinear.gravity = Gravity.CENTER
//        gameLinear.setBackgroundResource(R.color.white)

        for(y in 1..FIELD_HEIGHT){
            val rowLinear = LinearLayoutCompat(activity as Context)
            rowLinear.layoutParams = VGLayoutParams(gameLinearWidth, squareSide)

            for(x in 1..FIELD_WIDTH) {
                val fieldLinear = LinearLayoutCompat(activity as Context)
                val paramsFieldLinear = VGLayoutParams(squareSide, squareSide)
                fieldLinear.background = ResourcesCompat.getDrawable(resources,
                    R.drawable.stroke, activity?.theme)
                fieldLinear.layoutParams = paramsFieldLinear
                rowLinear.addView(fieldLinear)
            }
            gameLinear.addView(rowLinear)
        }


        val scoreIndicator = layoutInflater.inflate(R.layout.score_indicator, mainLinear)
        scoreTv = scoreIndicator.findViewById(R.id.score_tv)
        bestScoreTv = scoreIndicator.findViewById(R.id.best_score_tv)

        scoreTv.text = "0"
        bestScoreTv.text = getString(R.string.best_score_tv, localBestScore.toString())

        mainLinear.addView(gameLinear)

        val controlButtons = layoutInflater.inflate(R.layout.control_buttons, mainLinear)


        /*val upButton = */controlButtons.findViewById<AppCompatImageButton>(R.id.up_button).apply { setOnClickListener(this@GameFragment) }
        /*val downButton = */controlButtons.findViewById<AppCompatImageButton>(R.id.down_button).apply { setOnClickListener(this@GameFragment) }
        /*val rightButton = */controlButtons.findViewById<AppCompatImageButton>(R.id.right_button).apply { setOnClickListener(this@GameFragment) }
        /*val leftButton = */controlButtons.findViewById<AppCompatImageButton>(R.id.left_button).apply { setOnClickListener(this@GameFragment) }

        gameRunner = Thread(GameRunner(activity as AppCompatActivity, ::render, ::updateScore, ::updateBestScore)).apply { start() }
        return mainLinear
    }

    private fun updateScore() {
        scoreTv.text = getString(R.string.score_tv, currentScore.toString())
    }

    private fun updateBestScore() {
        if(currentScore > localBestScore) {
            localBestScore = currentScore
            bestScoreTv.text = getString(R.string.best_score_tv, localBestScore.toString())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        GameFragment.gameRunner?.interrupt()
        GameFragment.gameRunner = null
        log("gameFragment onDestroy")
    }


    private fun render() {
        for (y in 0 until FIELD_HEIGHT) {
            for (x in 0 until FIELD_WIDTH) {
                (gameLinear.getChildAt(y) as LinearLayoutCompat).getChildAt(x).setBackgroundResource(
                    (toColor[selectedMap[y][x]])!!
                )
            }
        }
    }

    override fun onClick(v: View?) {
        onClickThreadLauncher(v)
    }

    private fun onClickThreadLauncher(v: View?) {
        if (onClickThreadInstance.state != Thread.State.WAITING && onClickThreadInstance.state != Thread.State.RUNNABLE) {
            onClickThreadInstance = OnClickThread(v)
            onClickThreadInstance.start()
        }
    }

    private var onClickThreadInstance = OnClickThread(null)

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private class OnClickThread(var v: View?) : Thread() {
        override fun run() {
            when(v?.id) {
                R.id.up_button -> {
                    Snake.direction = Direction.UP
                }
                R.id.down_button -> {
                    Snake.direction = Direction.DOWN
                }
                R.id.right_button -> {
                    Snake.direction = Direction.RIGHT
                }
                R.id.left_button -> {
                    Snake.direction = Direction.LEFT
                }
            }

            if(!isInMove) {
                synchronized(isInMove) {
                    (isInMove as Object).wait()
                }
            }else {
                synchronized(isInMove) {
                    (isInMove as Object).wait()
                }
                synchronized(isInMove) {
                    (isInMove as Object).wait()
                }
            }
        }
    }
}