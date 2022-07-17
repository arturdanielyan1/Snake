package com.bignerdranch.android.snake.maps

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.main.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MapsFragment : Fragment()/*, View.OnClickListener*/ {

    companion object {
        @JvmStatic fun newInstance() = MapsFragment()
    }

//    private lateinit var mapPreviewImg: LinearLayout
//    private lateinit var mapPreviewImgParent: ConstraintLayout

    private lateinit var nextMapButton: ImageButton
    private lateinit var previousMapButton: ImageButton
    private lateinit var mapNameTv: TextView

    private lateinit var heightEt: EditText
    private lateinit var widthEt: EditText
    private lateinit var mapSizeLL: LinearLayoutCompat

    private lateinit var mapsTabLayout: TabLayout
    private lateinit var mapsViewPager: ViewPager2

    private var currentViewingMapIndex = 0 // after changing to viewPager getter of this field has changed
        set(value) {
            if(value == 0 || value == 1) {
                mapSizeLL.visibility = View.VISIBLE
            }else {
                mapSizeLL.visibility = View.INVISIBLE
            }
            if(value == -1) field = mapIndexToImg.size
            field = value
        }

    private lateinit var animation: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        mapSizeLL = v.findViewById(R.id.map_size_ll)

//        mapNameTv = v.findViewById<TextView>(R.id.map_name_tv).apply { text = mapNames[currentViewingMapIndex] }
        currentViewingMapIndex = selectedMapIndex
//        mapPreviewImg = v.findViewById(R.id.map_preview_ll)
//        mapPreviewImg.setBackgroundResource(mapIndexToImg[currentViewingMapIndex]!!)

//        nextMapButton = v.findViewById<ImageButton>(R.id.next_map_button).apply { setOnClickListener(this@MapsFragment) }
//        previousMapButton = v.findViewById<ImageButton>(R.id.previous_map_button).apply { setOnClickListener(this@MapsFragment) }

//        mapPreviewImgParent = v.findViewById(R.id.maps_parent)


        val toastMaxFieldSize = { Toast.makeText(activity, R.string.max_field_toast, Toast.LENGTH_SHORT).show() }
        heightEt = v.findViewById<EditText>(R.id.height_et).apply { addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newValue = try { s.toString().toInt() } catch (e: NumberFormatException) { setText("0"); 0 }

                if (s.toString().length > 1 && s.toString()[0] == '0') setText(s.toString().substring(1))
                if(newValue.toString().toInt() > 50){
                    setText("50")
                    toastMaxFieldSize()
                }else {
                    FIELD_HEIGHT = newValue
                }
                setSelection(text.toString().length)
            }

        }) }
        widthEt = v.findViewById<EditText>(R.id.width_et).apply { addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newValue = try { s.toString().toInt() } catch (e: NumberFormatException) { setText("0"); 0 }

                if (s.toString().length > 1 && s.toString()[0] == '0') setText(s.toString().substring(1))
                if(newValue.toString().toInt() > 50){
                    setText("50")
                    toastMaxFieldSize()
                }else {
                    FIELD_WIDTH = newValue
                }
                setSelection(text.toString().length)
            }

        }) }

        heightEt.setText(FIELD_HEIGHT.toString())
        widthEt.setText(FIELD_WIDTH.toString())


        // MAPS VIEW PAGER
        mapsViewPager = v.findViewById(R.id.maps_view_pager)
        log("Selected map index is $selectedMapIndex")


        mapsViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                log("created $position fragment")
                return MapPreviewPagerFragment.newInstance(position)
            }

            override fun getItemCount() =
                mapNames.size
        }



        mapsTabLayout = v.findViewById(R.id.maps_tab_layout)
        TabLayoutMediator(mapsTabLayout, mapsViewPager) { tab, position ->
            tab.text = mapNames[position]
        }.attach()

        mapsViewPager.setCurrentItem(currentViewingMapIndex, false)

        mapsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentViewingMapIndex = position
                log("page Selected $position")
            }
        })
    }

//    override fun onClick(v: View?) {
//        when(v?.id) {
//            R.id.next_map_button -> {
//                if(!::animation.isInitialized || animation.hasEnded()) {
//                    currentViewingMapIndex++
//                    changePreview(true)
//                }
//            }
//            R.id.previous_map_button -> {
//                if(!::animation.isInitialized || animation.hasEnded()) {
//                    currentViewingMapIndex--
//                    changePreview(false)
//                }
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        if(widthEt.text.toString().toInt() < 5 && heightEt.text.toString().toInt() < 5) {
            FIELD_HEIGHT = 5
            FIELD_WIDTH = 5
            Toast.makeText(activity, R.string.invalid_field_size, Toast.LENGTH_SHORT).show()
        }
        else if (widthEt.text.toString().toInt() < 5) {
            FIELD_WIDTH = 5
            Toast.makeText(activity, R.string.invalid_width, Toast.LENGTH_SHORT).show()
        }
        else if (heightEt.text.toString().toInt() < 5) {
            FIELD_HEIGHT = 5
            Toast.makeText(activity, R.string.invalid_height, Toast.LENGTH_SHORT).show()
        }

        if(currentViewingMapIndex != 0 && currentViewingMapIndex != 1) {
            FIELD_HEIGHT = mapIndexToMatrix[currentViewingMapIndex]!!.get().size
            FIELD_WIDTH = mapIndexToMatrix[currentViewingMapIndex]!!.get()[0].size
        }else {
            FIELD_HEIGHT = heightEt.text.toString().toInt()
            FIELD_WIDTH = widthEt.text.toString().toInt()
        }

        selectedMapIndex = currentViewingMapIndex

    }

//    private fun changePreview(next: Boolean) {
//        mapNameTv.text = mapNames[currentViewingMapIndex]
//
//        val newMapPreviewImg = layoutInflater.inflate(R.layout.map_img, mapPreviewImgParent, false)
//        mapPreviewImg.startAnimation(AnimationUtils.loadAnimation(activity, if (next) R.anim.maps_preview_disappear_left else R.anim.maps_preview_disappear_right))
//        (mapPreviewImg.parent as ViewGroup).removeView(mapPreviewImg)
//
//        newMapPreviewImg.setBackgroundResource(mapIndexToImg[currentViewingMapIndex]!!)
//        mapPreviewImgParent.addView(newMapPreviewImg)
//        animation = AnimationUtils.loadAnimation(activity, if (next) R.anim.maps_preview_appear_right else R.anim.maps_preview_appear_left)
//        newMapPreviewImg.startAnimation(animation)
//        mapPreviewImg = newMapPreviewImg as LinearLayout
//    }
}