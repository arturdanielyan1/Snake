package com.bignerdranch.android.snake.otherPages

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.main.FragmentLauncher


class MenuFragment : Fragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = MenuFragment()
    }

    private lateinit var fragmentLauncher: FragmentLauncher

    private lateinit var playButton: Button
    private lateinit var settingsButton: Button
    private lateinit var mapsButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        playButton = v.findViewById<Button>(R.id.play_button).apply { setOnClickListener(this@MenuFragment) }
        settingsButton = v.findViewById<Button>(R.id.settings_button).apply { setOnClickListener(this@MenuFragment) }
        mapsButton = v.findViewById<Button>(R.id.maps_button).apply { setOnClickListener(this@MenuFragment) }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.play_button -> {
                fragmentLauncher.openGameFragment()
            }
            R.id.settings_button -> {
                fragmentLauncher.openCustomizeFragment()
            }
            R.id.maps_button -> {
                fragmentLauncher.openMapsFragment()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentLauncher = activity as FragmentLauncher
    }
}