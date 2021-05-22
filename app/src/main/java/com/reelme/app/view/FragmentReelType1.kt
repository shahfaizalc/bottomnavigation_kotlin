package com.reelme.app.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.GestureListener
import com.reelme.app.R
import com.reelme.app.databinding.ContentChallengeBinding
import com.reelme.app.databinding.FragmentReeltype4Binding
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.util.MultipleClickHandler
import com.reelme.app.viewmodel.ChallengeModel
import com.reelme.app.viewmodel.ReelTypeMobileViewModel


class FragmentReelType1: BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: ReelTypeMobileViewModel


     var binding : FragmentReeltype4Binding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<FragmentReeltype4Binding>(inflater, R.layout.fragment_reeltype4, container, false)
            areaViewModel = ReelTypeMobileViewModel(this.context!!, this)
            var activity = this.activity;
            binding?.homeData = areaViewModel

            val gdt = GestureDetector(activity, GestureListener())

            binding?.swipeDaily2!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    gdt.onTouchEvent(event);
                    Log.d("fai", "You have swipped left swipeDaily")
                    binding?.homeData!!.onFilterClick()
                    return true
                }
            })

            binding?.swipeAdventures!!.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    gdt.onTouchEvent(event);
                    Log.d("fai", "You have swipped left swipeAdventures")
                    if (!MultipleClickHandler.handleMultipleClicks()) {
                        binding?.homeData!!.signInUserClicked()
                    }
                    return true
                }
            })
        }
        return binding!!.root
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
