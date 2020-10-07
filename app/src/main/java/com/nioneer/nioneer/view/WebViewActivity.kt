package com.nioneer.nioneer.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ActivityWebviewBinding
import com.nioneer.nioneer.rss.utils.Constantss.Companion.BUNDLE_KEY_URL
import com.nioneer.nioneer.rss.utils.NetworkStateHandler
import com.nioneer.nioneer.viewmodel.WebViewModel


/**
 * the activity to show the blog article on web view based on the user selection.
 */

class WebViewActivity : AppCompatActivity(), NetworkStateHandler.NetworkStateListener {

    //TAG: Class name
    private val TAG = "WebViewActivity"

    //To handle the network state change
    lateinit var networkStateHandler: NetworkStateHandler

    //Binding
    @Transient
    lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val blogUrl = intent.getStringExtra(BUNDLE_KEY_URL)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        binding.webViewData = WebViewModel()
        binding.webViewData!!.webViewUrl = blogUrl
        binding.executePendingBindings()
        networkStateHandler = NetworkStateHandler()
    }

    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unRegisterListeners()
    }

    /**
     * Register network state handler
     */
     fun registerListeners() {
        networkStateHandler.registerNetWorkStateBroadCast(this)
        networkStateHandler.setNetworkStateListener(this)
    }

    /**
     * To Unregister network state handler
     */
     fun unRegisterListeners() {
        networkStateHandler.unRegisterNetWorkStateBroadCast(this)
    }

    /**
     * To handle on network state change received.
     * @param online: network state
     */
    override fun onNetworkStateReceived(online: Boolean) {
        Log.d(TAG, "onNetWorkStateReceived :$online")
        with(binding.webViewData!!) {
            when (online) {
                true -> {
                    msgView = android.view.View.GONE
                }
                false -> {
                    msgView = android.view.View.VISIBLE
                    msg = applicationContext.resources.getString(R.string.network_ErrorMsg)
                }
            }
        }
    }
}
