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
import com.reelme.app.databinding.FragmentReeldailybonusBinding
import com.reelme.app.databinding.FragmentReeltype4Binding
import com.reelme.app.listeners.BonusTopicsResultListener
import com.reelme.app.model2.BonusTopics
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.viewmodel.ReelDailyBonusMobileViewModel
import com.reelme.app.viewmodel.ReelTypeMobileViewModel


class FragmentReelDailyBonus : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ReelDailyBonusMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState)

        val binding : FragmentReeldailybonusBinding = DataBindingUtil.setContentView(this, R.layout.fragment_reeldailybonus)
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

        val firbaseWriteHandlerActivity = FirbaseWriteHandlerActivity(this)
        firbaseWriteHandlerActivity.doGetBonusTopics(object : BonusTopicsResultListener {


            override fun onSuccess(url: List<BonusTopics>) {
                Log.d("TAG", "Success bonus topics size " +url.size)
            }

            override fun onFailure(e: Exception) {
                Log.d("TAG", "Failure bonus topics size " +e.message)

            }
        })
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
