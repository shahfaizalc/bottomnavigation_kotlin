package com.reelme.app.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.GestureListener
import com.reelme.app.R
import com.reelme.app.databinding.FragmentReeldailybonusBinding
import com.reelme.app.viewmodel.ReelDailyBonusMobileViewModel


class FragmentReelDailyBonus : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ReelDailyBonusMobileViewModel

    @Transient
    lateinit var binding : FragmentReeldailybonusBinding ;

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState)

         binding  = DataBindingUtil.setContentView(this, R.layout.fragment_reeldailybonus)
        areaViewModel = ReelDailyBonusMobileViewModel(this, this)
        var activity = this;
        binding.homeData = areaViewModel

         val gdt = GestureDetector(activity, GestureListener())


//         binding.swipeDaily2.setOnTouchListener(object : View.OnTouchListener {
//             override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                 gdt.onTouchEvent(event);
//                 Log.d("fai", "You have swipped left swipeDaily")
//                 binding.homeData!!.onFilterClick()
//                 return true
//             }
//
//         })
//
//        binding.swipeAdventures.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                gdt.onTouchEvent(event);
//                Log.d("fai", "You have swipped left swipeAdventures")
//                binding.homeData!!.signInUserClicked()
//
//                return true
//            }
//
//        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        findViews();
        loadAnimations();
        changeCameraDistance();
        flipCard()

    }

    private var mSetRightOut: AnimatorSet? = null
    private var mSetLeftIn: AnimatorSet? = null
    private var mIsBackVisible = false
    private var mCardFrontLayout: View? = null
    private var mCardBackLayout: View? = null


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
        mCardBackLayout = binding.swipeDaily2
        mCardFrontLayout = binding.swipeAdventures
    }

    fun flipCard() {
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
