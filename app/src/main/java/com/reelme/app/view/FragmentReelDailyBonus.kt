package com.reelme.app.view

import android.animation.AnimatorInflater
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
import com.reelme.app.databinding.FragmentReeldailybonusBinding
import com.reelme.app.util.MultipleClickHandler.Companion.handleMultipleClicks
import com.reelme.app.viewmodel.ReelDailyBonusMobileViewModel
import kotlin.math.abs


class FragmentReelDailyBonus : AppCompatActivity() {

    private  val TAG = "FragmentReelDailyBonus"

    @Transient
    lateinit internal var areaViewModel: ReelDailyBonusMobileViewModel

    @Transient
    lateinit var binding: FragmentReeldailybonusBinding;

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

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_reeldailybonus)
        areaViewModel = ReelDailyBonusMobileViewModel(this, this)
        var activity = this;
        binding.homeData = areaViewModel


        val gdt = GestureDetector(activity, GestureListener())


        binding.fliplayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gdt.onTouchEvent(event);
                Log.d(TAG, "You have swipped left swipeDaily")
                counter++
                counterList.add(event!!.getY())
                if (event!!.action == MotionEvent.ACTION_MOVE) {
                    //do some thing
                    Log.d(TAG, "setTouchListener ACTION_MOVE")
                    startRecording()
                } else {
                    Log.d(TAG, "setTouchListener ACTION_-" + event!!.action)
                }
                return true
            }

        })

        binding.fliplayout2.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gdt.onTouchEvent(event);

                Log.d(TAG, "You have swipped left swipeAdventures" + event!!.getY())
                counter++
                counterList.add(event!!.getY())
                if (event!!.action == MotionEvent.ACTION_MOVE) {
                    //do some thing
                    Log.d(TAG, "setTouchListener ACTION_MOVE")
                    startRecording()
                } else {
                    Log.d(TAG, "setTouchListener ACTION_-" + event!!.action)
                }

                return true
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        findViews();
        loadAnimations();
        changeCameraDistance();


    }

    fun startRecording(){
        if (!handleMultipleClicks()) {
            startActivity(Intent(this, RecordActivity::class.java));
        }}


    private fun changeCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        mCardFrontLayout!!.cameraDistance = scale
        mCardBackLayout!!.cameraDistance = scale

    }

    private fun loadAnimations() {
        mSetRightOut = AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_in) as AnimatorSet
        mSetLeftIn = AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_out) as AnimatorSet
    }

    private fun findViews() {
        mCardBackLayout = binding.fliplayout
        mCardFrontLayout = binding.fliplayout2
    }

    private fun flipCard() {
        mIsBackVisible = if (!mIsBackVisible) {
            mSetRightOut!!.setTarget(mCardFrontLayout)
            mSetLeftIn!!.setTarget(mCardBackLayout)
            mSetRightOut!!.start()
            mSetLeftIn!!.start()
            true
        } else {
            mSetRightOut!!.setTarget(mCardBackLayout)
            mSetLeftIn!!.setTarget(mCardFrontLayout)
            mSetRightOut!!.start()
            mSetLeftIn!!.start()
            false
        }
        // loadItems()
    }

//    fun loadItems() {
//        Log.d("fai", "You have  counter$counter")
//
//        if (counter == 3) {
//
//            if (!handleMultipleClicks()) {
//
//                Log.d(TAG, "You have swipped left counter" + ((counterList[2] - counterList[0])))
//
//                Log.d(TAG, "You have swipped left counter" + (abs(counterList[2] - counterList[0]) > 50))
//
//                    if (counterList[0] > counterList[2]) {
//                        // is swipe up
//                        if (areaViewModel.totalItems != (areaViewModel.endItem + 1)) {
//                            flipCard()
//                            if (areaViewModel.endItem + 2 != areaViewModel.totalItems) {
//                                areaViewModel.loadBothItems(areaViewModel.endItem + 1, areaViewModel.endItem + 2)
//                            } else {
//                                areaViewModel.loadSingleItem(areaViewModel.endItem + 1)
//                            }
//                        }
//                    } else {
//                        // is swipe down
//                        if (areaViewModel.endItem > 1) {
//                            flipCard()
//                            if (areaViewModel.totalItems != 3) {
//                                areaViewModel.loadBothItems(areaViewModel.startItem - 2, areaViewModel.endItem - 2)
//                            } else {
//                                areaViewModel.loadSingleItem(areaViewModel.endItem - 1)
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
