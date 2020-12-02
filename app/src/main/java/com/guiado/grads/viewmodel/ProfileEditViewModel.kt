package com.guiado.grads.viewmodel

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.model.IndiaItem

import com.guiado.grads.BR
import com.guiado.grads.Events.MyCustomEvent
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.MultipleClickListener
import com.guiado.grads.model2.Profile
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.view.FragmentProfileEdit
import com.google.firebase.auth.FirebaseAuth
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList


class ProfileEditViewModel(private val context: Context, private val fragmentSignin: FragmentProfileEdit, postAdObj: String) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var profile = Profile();
    val dialog = Dialog(context)
    var  observableArrayList =  ArrayList<IndiaItem>()

    init {
        networkHandler()
        profile = (GenericValues().getProfile(postAdObj, fragmentSignin))
        readAutoFillItems()
        observableArrayList = readAutoFillItems2()

    }

    private fun readAutoFillItems() {
        val c = GenericValues()
    }

    @get:Bindable
    var showSignUpProgress: Int = View.INVISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpProgress)
        }

    @get:Bindable
    var showSignUpBtn: Int = View.VISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpBtn)
        }
    /*
   Method will act as the event handler for MyCustomEvent.kt
   */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
      //  userAddress = getAddress()
    }

    private fun getAddress() = " " + profile.address?.locationname + "\n " + profile.address?.streetName +
            ", " + profile.address?.town + "\n " + profile.address?.city

    private fun getLocation() = profile.location





    var imgUrl = ""


    @get:Bindable
    var userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
        set(price) {
            field = price
            profile.email = price
            notifyPropertyChanged(BR.userEmail)

        }

    fun showProgresss(isShow : Boolean){
        if(isShow){
            showSignUpBtn = View.INVISIBLE
            showSignUpProgress = View.VISIBLE }
        else{
            showSignUpBtn = View.VISIBLE
            showSignUpProgress = View.INVISIBLE
        }
    }
    fun datePickerClick() = View.OnClickListener {

    }

    fun registerEventBus(){
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }


    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(context)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(context)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }

    override fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }




    @get:Bindable
    var userLocation: String? = getLocation()
        set(price) {
            field = price
            notifyPropertyChanged(BR.userLocation)

            profile.location = field
        }
    private fun readAutoFillItems2() : ArrayList<IndiaItem> {
        val values = GenericValues()
        return values.readAutoFillItems(context)
    }

    @Override
    fun onFilterClick() = View.OnClickListener() {

        if(!handleMultipleClicks()) {
        }
    }
}
