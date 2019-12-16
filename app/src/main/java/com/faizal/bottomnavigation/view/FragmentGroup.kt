package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentGroupBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model2.Comments
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.GroupViewModel


class FragmentGroup : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: GroupViewModel

    lateinit var binding : FragmentGroupBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)

        return bindView(inflater, container, postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        binding = DataBindingUtil.inflate<FragmentGroupBinding>(inflater, R.layout.fragment_group, container, false)
        areaViewModel = GroupViewModel(this.context!!, this,postAdObj)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.groups
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainDataModel?.userIds?.observe(this, object: Observer<List<Comments>> {
            override fun onChanged(t: List<Comments>?) {

                if (t != null) {
                    binding.mainDataModel?.adapter?.setData(t)
                }
            }
        } )

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
