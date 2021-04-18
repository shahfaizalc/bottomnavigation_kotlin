package com.reelme.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.adapter.UserAdapter
import com.reelme.app.databinding.UserActivityBinding
import com.reelme.app.viewmodel.UserViewModel

class UserAcivity  : AppCompatActivity() {

    private val viewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: UserActivityBinding = DataBindingUtil.setContentView(this, R.layout.user_activity)



        binding.viewModel = viewModel

        val adapter = UserAdapter(binding.viewModel!!)
        binding.recyclerView.adapter = adapter
        viewModel.startUpdates()
    }

    override fun onDestroy() {
        viewModel.stopUpdates()
        super.onDestroy()
    }
}