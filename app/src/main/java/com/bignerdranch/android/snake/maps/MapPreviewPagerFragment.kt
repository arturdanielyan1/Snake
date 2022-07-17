package com.bignerdranch.android.snake.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.main.mapIndexToImg

class MapPreviewPagerFragment : Fragment() {

    companion object {
        @JvmStatic val EXTRA_MAP_POSITION = "puttingPositionForViewPager"

        @JvmStatic fun newInstance(mapPosition: Int) =
            MapPreviewPagerFragment().apply {
                val bundle = Bundle()
                bundle.putInt(EXTRA_MAP_POSITION, mapPosition)
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_preview_pager, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        val mapIndex = arguments?.getInt(EXTRA_MAP_POSITION, 0)
        val mapPreviewLl = v.findViewById<LinearLayout>(R.id.map_preview_pager)
        mapPreviewLl.setBackgroundResource(mapIndexToImg[mapIndex]!!)
    }
}