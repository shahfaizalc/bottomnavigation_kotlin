package com.reelme.app.view

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentReeltype4Binding
import com.reelme.app.viewmodel.ReelTypeMobileViewModel


class FragmentReelType4 : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: ReelTypeMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState)

        val binding : FragmentReeltype4Binding = DataBindingUtil.setContentView(this, R.layout.fragment_reeltype4)
        areaViewModel = ReelTypeMobileViewModel(this, this)
        binding.homeData = areaViewModel
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
