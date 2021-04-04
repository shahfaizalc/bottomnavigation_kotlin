package com.reelme.app.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.reelme.app.viewmodel.SalesViewModel
import com.reelme.app.R
import com.reelme.app.databinding.FragmentDetailsBinding
import com.reelme.app.fragments.BaseFragment
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.model.Sale
import com.reelme.app.util.RequestType

class SaleWomenFragment : BaseFragment(), NetworkChangeHandler.NetworkChangeListener {

    /**
     * Binding
     */
    internal var binding: FragmentDetailsBinding? = null

    /**
     * Network change listener
     */
    lateinit var networkChangeHandler: NetworkChangeHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentDetailsBinding.inflate(inflater, container, false)
            val areaViewModel = ViewModelProviders.of(this).get(SalesViewModel::class.java)

            setBindingAttributes(areaViewModel)
        }
        return binding!!.root
    }

    private fun setBindingAttributes(areaViewModel: SalesViewModel) {
        binding!!.mainData = areaViewModel
        binding!!.included.mainData = areaViewModel
        doRequest()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkChangeHandler = NetworkChangeHandler()
        binding!!.included.retry.setOnClickListener { view1 -> doRequest() }
    }


    private fun doRequest() {
        binding!!.mainData!!.progressBarVisible = View.VISIBLE
        binding!!.mainData!!.getAllSalesList(RequestType.WOMEN).observe(this,object : Observer<List<Sale>> {
            override fun onChanged(t: List<Sale>?) {
                binding!!.mainData!!.saleItemsAdapter!!.setSalesItems(t!!)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        networkChangeHandler.unRegisterNetWorkStateBroadCast(this.context!!)
    }

    override fun onResume() {
        super.onResume()
        networkChangeHandler.setNetworkStateListener(this)
        networkChangeHandler.registerNetWorkStateBroadCast(this.context!!)
    }

    override fun networkChangeReceived(state: Boolean) {
        binding!!.included.retry.isEnabled = state
        if (!state) {
            Toast.makeText(this.context, R.string.network_ErrorMsg, Toast.LENGTH_LONG).show()
        }
    }

//    override fun onConfigurationChanged(newConfig: Configuration?) {
//        super.onConfigurationChanged(newConfig)
//        val columns = context!!.getResources().getInteger(R.integer.gallery_columns);
//        binding!!.mainData!!.linearLayoutManager!!.setSpanCount(columns);
//
//    }
}
