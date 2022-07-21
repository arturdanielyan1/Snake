package com.bignerdranch.android.snake.otherPages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.database.InfoViewModel
import com.bignerdranch.android.snake.main.*

class GameOverFragment : DialogFragment(), View.OnClickListener {

    companion object {
        @JvmStatic fun newInstance() = GameOverFragment()
    }


    private lateinit var fragmentLauncher: FragmentLauncher

    private lateinit var tryAgainButton: Button
    private lateinit var mainMenuButton: Button

    private lateinit var viewModel: InfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as SharedViewModel).getViewModel()
        isCancelable = false
        if(currentScore >= localBestScore){
            localBestScore = currentScore
            viewModel.changeBestScore(localBestScore)
        }
        log("GameOver current score is $currentScore")
        viewModel.changeLastScore(currentScore)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_game_over, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        tryAgainButton = v.findViewById<Button>(R.id.try_again_button).apply { setOnClickListener(this@GameOverFragment) }
        mainMenuButton = v.findViewById<Button>(R.id.main_menu_button).apply { setOnClickListener(this@GameOverFragment) }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.try_again_button -> {
                activity?.supportFragmentManager?.popBackStack(GAME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fragmentLauncher.openGameFragment()
            }
            R.id.main_menu_button -> {
                fragmentLauncher.openMenuFragment()
            }
        }
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentLauncher = activity as FragmentLauncher
    }
}