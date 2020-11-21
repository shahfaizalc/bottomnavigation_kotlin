package com.guiado.grads.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.databinding.DataBindingUtil

import com.guiado.grads.R
import com.guiado.grads.databinding.ContentDiscussionBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.viewmodel.DiscussionModel


class FragmentDiscussions : BaseFragment() {

    var binding: ContentDiscussionBinding? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding == null) {
            binding = DataBindingUtil.inflate<ContentDiscussionBinding>(inflater, R.layout.content_discussion, container, false)
            val areaViewModel = DiscussionModel(activity!!, this)
            binding?.adSearchModel = areaViewModel
        }
        return binding!!.root
    }


    override fun onResume() {
        Log.d("on Resume","on Resuem called")
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     //   super.onActivityResult(requestCode, resultCode, data)
        Log.d("on result faizal", "on result faizal $resultCode")

    }

}
