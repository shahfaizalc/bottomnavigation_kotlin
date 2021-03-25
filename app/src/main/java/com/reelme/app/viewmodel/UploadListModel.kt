package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.reelme.app.BR
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.view.FragmentBioMobile
import com.reelme.app.view.FragmentUploadView


class UploadListModel(internal var context: Context,
                      internal var fragment: FragmentUploadView) :
        NetworkChangeHandler.NetworkChangeListener, BaseObservable() {

    val TAG = "ExamYearsListModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    var openChooserLiveData = MutableLiveData<Boolean>()


    init {
        networkHandler()
        registerListeners()
    }

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var retry = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.retry)
        }

    @get:Bindable
    var msgView = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msgView)
        }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    fun retryBtnClick() = View.OnClickListener {

    }

    fun signInUserClicked() = View.OnClickListener {
        //  fragmentSignin.finish()
        fragment.startActivity(Intent(fragment, FragmentBioMobile::class.java));
    }


    fun openFileChosser() = View.OnClickListener {
        openChooserLiveData.setValue(true);
    }

    fun initFileChooser(): LiveData<Boolean> {
        return openChooserLiveData
    }

    fun registerListeners() {
        networkStateHandler?.registerNetWorkStateBroadCast(context)
        networkStateHandler?.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler?.unRegisterNetWorkStateBroadCast(context)
    }

    override fun networkChangeReceived(state: Boolean) {

        when (state) {
            true -> msgView = View.GONE
            false -> {
                msgView = View.VISIBLE
                msg = context.resources.getString(R.string.network_ErrorMsg)
                networkError()
            }
        }
    }

    private fun networkError() {
        Toast.makeText(context, context.resources.getText(R.string.network_ErrorMsg), Toast.LENGTH_SHORT).show()
    }

}