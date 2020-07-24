package com.guiado.akbhar.communication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ActivityHome2Binding
import com.guiado.akbhar.databinding.ContentArtBinding
import com.guiado.akbhar.viewmodel.ArtViewModel

/**
 * Home Activity to show list of blog articles
 */

class  HomeActivity :  AppCompatActivity() {

    @Transient
    var binding: ActivityHome2Binding? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_home2);

            val areaViewModel = HomeViewModel2()
            binding?.viewModelData = areaViewModel
        }
    }

}
