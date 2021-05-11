package com.reelme.app.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import com.reelme.app.GestureListener
import com.reelme.app.R
import com.reelme.app.databinding.FragmentAdventuresBinding
import com.reelme.app.databinding.FragmentReeldailybonusBinding
import com.reelme.app.util.MultipleClickHandler.Companion.handleMultipleClicks
import com.reelme.app.viewmodel.ReelAdventuresMobileViewModel
import com.reelme.app.viewmodel.ReelDailyBonusMobileViewModel
import kotlin.math.abs


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
                Log.d("fai", "You have swipped left swipeDaily")
                counter++
                counterList.add(event!!.getY())
                loadItems()
                return true
            }

        })

        binding.swipeDaily2.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                gdt.onTouchEvent(event);

                Log.d("fai", "You have swipped left swipeAdventures" + event!!.getY())
                counter++
                counterList.add(event!!.getY())
                loadItems()
                return true
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        findViews();
        loadAnimations();
        changeCameraDistance();


    }


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

    fun loadItems() {
        Log.d("fai", "You have  counter$counter")

        if (counter == 3) {

            if (!handleMultipleClicks()) {

                Log.d("fai", "You have swipped left counter" + ((counterList[2] - counterList[0])))

                Log.d("fai", "You have swipped left counter" + (abs(counterList[2] - counterList[0]) > 50))

                    if (counterList[0] > counterList[2]) {
                        // is swipe up
                        if (areaViewModel.totalItems != (areaViewModel.endItem + 1)) {
                            flipCard()
                            if (areaViewModel.endItem + 1 != areaViewModel.totalItems) {
                                areaViewModel.loadBothItems(areaViewModel.endItem + 1, areaViewModel.endItem + 2)
                            } else {
                                areaViewModel.loadSingleItem(areaViewModel.endItem + 1)
                            }
                        }
                    } else {
                        // is swipe down
                        if (areaViewModel.endItem > 1) {
                            flipCard()
                            if (areaViewModel.totalItems != 3) {
                                areaViewModel.loadBothItems(areaViewModel.startItem - 2, areaViewModel.endItem - 2)
                            } else {
                                areaViewModel.loadSingleItem(areaViewModel.endItem - 1)
                            }
                        }
                    }

            }
            counter = 0
            counterList.clear()

        }
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
