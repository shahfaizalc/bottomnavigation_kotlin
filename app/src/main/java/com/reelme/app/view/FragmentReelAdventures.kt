package com.reelme.app.view

import android.animation.AnimatorSet
import android.content.Intent
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
import com.reelme.app.databinding.FragmentAdventuresBinding
import com.reelme.app.util.MultipleClickHandler.Companion.handleMultipleClicks
import com.reelme.app.viewmodel.ReelAdventuresMobileViewModel


class FragmentReelAdventures : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ReelAdventuresMobileViewModel

    @Transient
    lateinit var binding: FragmentAdventuresBinding;

    var counter = 0

    private var mSetRightOut: AnimatorSet? = null
    private var mSetLeftIn: AnimatorSet? = null
    private var mIsBackVisible = false
    private var mCardFrontLayout: View? = null
    private var mCardBackLayout: View? = null
    var counterList = ArrayList<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_adventures)
        areaViewModel = ReelAdventuresMobileViewModel(this, this)
        var activity = this;
        binding.homeData = areaViewModel


        val gdt = GestureDetector(activity, GestureListener())

        binding.swipeAdventures.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gdt.onTouchEvent(event);
                Log.d("fai", "You have swipped left Type A")
                counter++
                counterList.add(event!!.getY())
             //   loadItems()
                startRecording()
                return true
            }

        })

        binding.swipeDaily2.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gdt.onTouchEvent(event);

                Log.d("fai", "You have swipped left Type B" + event!!.getY())
                counter++
                counterList.add(event!!.getY())
              //  loadItems()
                startRecording();
                return true
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    }

   fun startRecording(){
       if (!handleMultipleClicks()) {
           startActivity(Intent(this, RecordActivity::class.java));
       }}

//    fun loadItems() {
//        Log.d("fai", "You have  counter$counter")
//
//        if (counter == 3) {
//
//            if (!handleMultipleClicks()) {
//
//                Log.d("fai", "You have swipped left counter" + ((counterList[2] - counterList[0])))
//
//                Log.d("fai", "You have swipped left counter" + (abs(counterList[2] - counterList[0]) > 50))
//
//                    if (counterList[0] > counterList[2]) {
//                        // is swipe up
//                        if (areaViewModel.totalItems != (areaViewModel.bItem + 1)) {
//                            if (areaViewModel.bItem + 1 != areaViewModel.totalItems) {
//                                areaViewModel.loadBothItems(areaViewModel.bItem + 1, areaViewModel.bItem + 2)
//                            } else {
//                                areaViewModel.loadSingleItem(areaViewModel.bItem + 1)
//                            }
//                        }
//                    } else {
//                        // is swipe down
//                        if (areaViewModel.bItem > 1) {
//                            if (areaViewModel.totalItems != 3) {
//                                areaViewModel.loadBothItems(areaViewModel.aItem - 2, areaViewModel.bItem - 2)
//                            } else {
//                                areaViewModel.loadSingleItem(areaViewModel.bItem - 1)
//                            }
//                        }
//                    }
//
//            }
//            counter = 0
//            counterList.clear()
//
//        }
//    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }
}
