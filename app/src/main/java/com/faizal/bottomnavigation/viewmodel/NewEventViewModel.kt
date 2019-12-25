package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.Events.MyCustomEvent
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.listeners.MultipleClickListener
import com.faizal.bottomnavigation.model2.*
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.*
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.*
import com.google.firebase.auth.FirebaseAuth
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.listeners.TimeListener
import com.itravis.ticketexchange.utils.DatePickerEvent
import com.itravis.ticketexchange.utils.TimePickerEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class NewEventViewModel(private val context: Context, private val fragmentSignin: FragmentNewEvent) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var profile : Profile


    init {
        networkHandler()
        profile = Profile();
    }


    @get:Bindable
    var userTitle: String? =  ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userTitle)
        }

    @get:Bindable
    var userDesc: String? =  ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userDesc)
        }


    @get:Bindable
    var showDate: String? = null
        set(showDate) {
            field = showDate
            notifyPropertyChanged(BR.showDate)
        }

    @get:Bindable
    var expiryDate: String? = null
        set(showDate) {
            field = showDate
            notifyPropertyChanged(BR.expiryDate)
        }

    @get:Bindable
    var showTime: String? = null
        set(showTime) {
            field = showTime
            notifyPropertyChanged(BR.showTime)
        }

    @Override
    fun timePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            TimePickerEvent().onTimePickerClick(fragmentSignin.context!!, object : TimeListener {
                override fun onTimeSet(result: String) {
                    showTime = result;
                }
            })
        }
    }

    @Override
    fun datePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(fragmentSignin.context!!, object : DateListener {
                override fun onDateSet(result: String) {
                    showDate = result
                }
            })
        }
    }

    @Override
    fun expiryDatePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(fragmentSignin.context!!, object : DateListener {
                override fun onDateSet(result: String) {
                    expiryDate = result
                }
            })
        }
    }

    fun updateAddress() = View.OnClickListener {

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        val fragment = FragmentAddress()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
    }

    @get:Bindable
    var userAddress: String? = getAddress()
        set(price) {
            field = price
            notifyPropertyChanged(BR.userAddress)

        }

    /*
  Method will act as the event handler for MyCustomEvent.kt
  */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
         userAddress = getAddress()
    }



    private fun getAddress() = " " + profile.address?.locationname + "\n " + profile.address?.streetName +
            ", " + profile.address?.town + "\n " + profile.address?.city


    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if ( userTitle.isNullOrEmpty() || !userTitle!!.isValid() || userDesc.isNullOrEmpty()
                    || showDate.isNullOrEmpty()|| expiryDate.isNullOrEmpty()|| showTime.isNullOrEmpty()  ) {
                Toast.makeText(context, context.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( userTitle!!.isValid() ) {

                val events = Events();
                events.title= userTitle
                events.searchTags = compareLIt()
                events.description = userDesc
                events.address = profile.address
                events.endDate = expiryDate?.onDatePickerClick().toString()
                events.startDate = showDate?.onDatePickerClick().toString()
                events.eventTime = showTime
                events.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                events.postedDate = System.currentTimeMillis().toString()
                events.postedByName = getUserName(context.applicationContext, FirebaseAuth.getInstance().currentUser?.uid!!).name!!

                Log.d(TAG, "DocumentSnapshot  doDiscussionWrrite "  )
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).updateEvents(events, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                        Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                        val fragment = FragmentTheEvents()
                        val bundle = Bundle()
                        fragment.setArguments(bundle)
                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

                    }
                })
            }
        }
    }

    private fun compareLIt(): List<String> { return userTitle!!.sentenceToWords()}


    fun String.isValid()= this.length > 8


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
}
