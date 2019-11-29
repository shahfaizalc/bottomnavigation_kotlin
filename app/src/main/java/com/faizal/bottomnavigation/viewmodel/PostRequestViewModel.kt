package com.faizal.bottomnavigation.viewmodel

import android.view.View
import android.widget.CompoundButton
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.listeners.AdSearchEventListener
import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentRequestComplete

class PostRequestViewModel(internal val activity: FragmentActivity, internal val fragmentProfileInfo: FragmentRequestComplete, internal val postAdObj: String)
    : BaseObservable() {
    companion object {

        private val TAG = "ProfileGalleryViewModel"
    }

    var profile: Profile
    init {

        profile = (GenericValues().getProfile(postAdObj, fragmentProfileInfo.context!!))

        setUpCardDetails();

    }

    private fun setUpCardDetails() {
        title = profile.title
        address = getAddress(profile.address)
        date = profile.moreInformation
        time = profile.name
        price = profile.desc
        discount = profile.title
        description = profile.email
    }

    private fun getAddress(address: Address?) = address!!.locationname + ", " + address.streetName + ", " + address.town  + ", " + address.city + ", " + address.state


    @get:Bindable
    var phonenumberVisible: Int = View.GONE
        set(phonenumberVisible) {
            field = phonenumberVisible
            notifyPropertyChanged(BR.phonenumberVisible)
        }

    @get:Bindable
    var phonenumber: String? = "91-7XXXXXXXXX"
        set(phonenumber) {
            field = phonenumber
            notifyPropertyChanged(BR.phonenumber)
        }

    @get:Bindable
    var title: String? = null
        set(title) {
            field = title
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var address: String? = null
        set(address) {
            field = address
            notifyPropertyChanged(BR.address)
        }

    @get:Bindable
    var date: String? = null
        set(date) {
            field = date
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var time: String? = null
        set(time) {
            field = time
            notifyPropertyChanged(BR.time)
        }

    @get:Bindable
    var price: String? = null
        set(price) {
            field = price
            notifyPropertyChanged(BR.price)
        }

    @get:Bindable
    var discount: String? = null
        set(discount) {
            field = discount
            notifyPropertyChanged(BR.discount)
        }

    @get:Bindable
    var offer: String? = null
        set(offer) {
            field = offer
            notifyPropertyChanged(BR.offer)
        }

    @get:Bindable
    var description: String? = null
        set(description) {
            field = description
            notifyPropertyChanged(BR.description)
        }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if (!handleMultipleClicks()) {

//         var myFirebaseMessagingService = MyFirebaseMessagingService();
//            myFirebaseMessagingService.sendNotification(null,activity.applicationContext)
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    fun updateKeyWords() = View.OnClickListener {
    }
}


