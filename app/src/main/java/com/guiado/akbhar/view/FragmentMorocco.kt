package com.guiado.akbhar.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.guiado.akbhar.R
import com.guiado.akbhar.adapter.CustomPagerAdapter
import com.guiado.akbhar.databinding.ContentMoroccoBinding
import com.guiado.akbhar.fragments.BaseFragment
import com.guiado.akbhar.viewmodel.MoroccoViewModel


class  FragmentMorocco : BaseFragment() {

    @Transient
    var binding: ContentMoroccoBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentMoroccoBinding>(inflater, R.layout.content_morocco, container, false)
            val areaViewModel = MoroccoViewModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
//        val viewPager = binding!!.viewpager
//        viewPager.adapter = CustomPagerAdapter(this.activity!!)
//        viewPager.autoScroll(3000)


        return binding!!.root
    }


    override fun onDestroy() {
//        adView.destroy()
        super.onDestroy()
    }


}
