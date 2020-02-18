package com.guiado.koodal.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.koodal.BR
import com.guiado.koodal.Events.MyCustomEvent
import com.guiado.koodal.R
import com.guiado.koodal.handler.NetworkChangeHandler
import com.guiado.koodal.listeners.EmptyResultListener
import com.guiado.koodal.listeners.MultipleClickListener
import com.guiado.koodal.model.CoachItem
import com.guiado.koodal.model2.PostEvents
import com.guiado.koodal.model2.Profile
import com.guiado.koodal.network.FirbaseWriteHandler
import com.guiado.koodal.util.GenericValues
import com.guiado.koodal.util.MultipleClickHandler
import com.guiado.koodal.utils.Constants
import com.guiado.koodal.view.FragmentAddress
import com.guiado.koodal.view.FragmentEvents
import com.guiado.koodal.view.FragmentKeyWords
import com.google.firebase.auth.FirebaseAuth
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.utils.DatePickerEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


class EventsViewModel(private val context: Context, private val fragmentSignin: FragmentEvents) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var profile = Profile();
    var postEvents = PostEvents();

    init {
        networkHandler()
        profile = Profile()
        postEvents = PostEvents();
        readAutoFillItems()
    }

    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.readCoachItems(context)

    }

    /*
   Method will act as the event handler for MyCustomEvent.kt
   */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
        postEvents.address = profile.address
        postEvents.keyWords = profile.keyWords
        userAddress = getAddress()
        keys = getKeyWords()

    }

    private fun getAddress() = " " + profile.address?.locationname + "\n " + profile.address?.streetName +
            "\n " + profile.address?.town + ", " + profile.address?.city

    private fun getKeyWords(): String {

        var result = ""

        val numbersIterator = profile.keyWords?.iterator()
        numbersIterator?.let {
            while (numbersIterator.hasNext()) {
                var value = (numbersIterator.next())
                result += " " + listOfCoachings!!.get(value - 1).categoryname
            }
        }

        return result;
    }


    @get:Bindable
    var showDate: String? = null
        set(showDate) {
            field = showDate
            postEvents.expiryDate = field
            notifyPropertyChanged(BR.showDate)
        }

    @Override
    fun datePickerClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(context!!, object : DateListener {
                override fun onDateSet(result: String) {
                    showDate = result
                }
            })
        }
    }

    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""


    @get:Bindable
    var userTitle: String? = profile.title
        set(price) {
            field = price
            postEvents.title = field
            notifyPropertyChanged(BR.userTitle)


        }

    @get:Bindable
    var userPhone: String? = profile.phone
        set(price) {
            field = price
            postEvents.contactInfo = field
            notifyPropertyChanged(BR.userPhone)

        }

    @get:Bindable
    var userDesc: String? = profile.desc
        set(price) {
            field = price
            postEvents.desc = field
            notifyPropertyChanged(BR.userDesc)

        }

    @get:Bindable
    var userAddress: String? = getAddress()
        set(price) {
            field = price
            notifyPropertyChanged(BR.userAddress)

        }

    @get:Bindable
    var keys: String? = getKeyWords()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keys)

        }

    fun doPostEvents() = View.OnClickListener {


        if (!handleMultipleClicks()) {
            if (postEvents.address == null && postEvents.keyWords == null &&
                    postEvents.title == null && postEvents.expiryDate == null) {
                return@OnClickListener
            }

            if (postEvents.address?.cityCode!!.toInt() > 0 && postEvents.keyWords!!.size > 0 && postEvents.title!!.length > 3 && postEvents.expiryDate!!.length  > 3) {
                postEvents.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                postEvents.postedDate = System.currentTimeMillis().toString()
                Log.d(TAG, "DocumentSnapshot onFailure i am in "  )
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).updatepostEvents(postEvents, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot onFailure " + e.message)
                        Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess ")
//                        val fragment = FragmentProfile()
//                        val bundle = Bundle()
//                        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//                        fragment.setArguments(bundle)
//                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

                    }
                })
            } else {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun updateAddress() = View.OnClickListener {
        EventBus.getDefault().register(this);
        val fragment = FragmentAddress()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
    }

    fun updateKeyWords() = View.OnClickListener {
        EventBus.getDefault().register(this);
        val fragment = FragmentKeyWords()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragment.setArguments(bundle)
        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
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
}
