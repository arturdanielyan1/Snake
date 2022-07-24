package com.bignerdranch.android.snake.otherPages

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bignerdranch.android.snake.R
import com.bignerdranch.android.snake.database.InfoViewModel
import com.bignerdranch.android.snake.main.SharedViewModel
import com.bignerdranch.android.snake.main.log
import com.bignerdranch.android.snake.main.stepDelay
import com.bignerdranch.android.snake.main.stepDelayDecrement

class SettingsFragment : Fragment() {

    companion object {
        @JvmStatic fun newInstance() = SettingsFragment()
    }


    private lateinit var mpsEt: EditText
    private lateinit var startSlowerCb: CheckBox
    private lateinit var decrementEt: EditText

    private lateinit var infoViewModel: InfoViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        infoViewModel = (activity as SharedViewModel).getViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        mpsEt = v.findViewById<EditText>(R.id.mps_et).apply { addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var newValue = try { s.toString().toInt() } catch (e: NumberFormatException) { setText("1"); 1 }

                if (s.toString().length > 1 && s.toString()[0] == '1' && s.toString()[1] != '0') {
                    setText(s.toString().substring(1))
                    newValue = text.toString().toInt()
                    Toast.makeText(activity, R.string.max_step_per_move, Toast.LENGTH_SHORT).show()
                }

                if(newValue > 10){
                    setText("10")
//                    Toast.makeText(activity, R.string.max_step_per_move, Toast.LENGTH_SHORT).show()
                }else {
                    stepDelay = 1000 / newValue.toLong()
                }
                setSelection(text.toString().length)
            }
        }) }

        mpsEt.setText((1000/ stepDelay).toInt().toString())


        decrementEt = v.findViewById(R.id.decrement_et)
        decrementEt.setText(stepDelayDecrement.toString())
        decrementEt = v.findViewById<EditText>(R.id.decrement_et).apply { addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {//3 50
                val newValue = try { s.toString().toInt() } catch (e: NumberFormatException) { -1 }
                log("onTextCHanged $s")
                if(newValue > 0) {
                    if(newValue > 50) {
                        setText("50")
                        setSelection(2)
                    }
                }
            }
        }) }

        startSlowerCb = v.findViewById<CheckBox>(R.id.start_slower_cb).apply { setOnCheckedChangeListener { _, isChecked ->
            mpsEt.isEnabled = !isChecked
            decrementEt.isEnabled = isChecked
        }}

        startSlowerCb.isChecked = infoViewModel.isSpeedingUp.value!!
        decrementEt.isEnabled = startSlowerCb.isChecked
        if (stepDelayDecrement < 3) decrementEt.setText("3")
        else decrementEt.setText(stepDelayDecrement.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        val decrement = try { decrementEt.text.toString().toInt() }
            catch (nfe: java.lang.NumberFormatException) {
                3
            }

        if(decrement < 3) {
            stepDelayDecrement = 3
            Toast.makeText(requireContext(), R.string.decrement_min, Toast.LENGTH_SHORT).show()
        }else stepDelayDecrement = decrement
        if(!decrementEt.isEnabled) {
            stepDelayDecrement = 0
        }

        infoViewModel.changeIsSpeedingUp(startSlowerCb.isChecked)
        infoViewModel.changeMPS(mpsEt.text.toString().toInt())
        infoViewModel.changeSpeedUpMillis(decrement)
    }
}