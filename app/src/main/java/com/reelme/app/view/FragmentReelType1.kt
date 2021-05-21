package com.reelme.app.view

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.GestureListener
import com.reelme.app.R
import com.reelme.app.databinding.FragmentReeltype4Binding
import com.reelme.app.util.MultipleClickHandler
import com.reelme.app.viewmodel.ReelTypeMobileViewModel


class FragmentReelType1 : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ReelTypeMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState)

        val binding : FragmentReeltype4Binding = DataBindingUtil.setContentView(this, R.layout.fragment_reeltype4)
        areaViewModel = ReelTypeMobileViewModel(this, this)
        var activity = this;
        binding.homeData = areaViewModel

         val gdt = GestureDetector(activity, GestureListener())


         binding.swipeDaily2.setOnTouchListener(object : View.OnTouchListener {
             override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                 gdt.onTouchEvent(event);
                 Log.d("fai", "You have swipped left swipeDaily")

                     binding.homeData!!.onFilterClick()

                 return true
             }

         })

        binding.swipeAdventures.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gdt.onTouchEvent(event);
                Log.d("fai", "You have swipped left swipeAdventures")


                if (!MultipleClickHandler.handleMultipleClicks()) {
                    binding.homeData!!.signInUserClicked()
                }

                return true
            }

        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }
}
