package com.faizal.bottomnavigation.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.CompoundButton
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentRequestComplete

class PostRequestViewModel(internal val activity: FragmentActivity, internal val fragmentProfileInfo: FragmentRequestComplete, internal val postAdObj: PostAdModel?)
    : BaseObservable() {
    companion object {

        private val TAG = "ProfileGalleryViewModel"
    }


    init {
        setUpCardDetails();

    }

    private fun setUpCardDetails() {
        title = postAdObj!!.title
        address = getAddress(postAdObj.address)
        date = postAdObj.showDate
        time = postAdObj.showTime
        price = postAdObj.ticketCount.toString() + " x " + postAdObj.price.toString()
        discount = postAdObj.discount.toString()
        offer = offerPrice(postAdObj)
        description = postAdObj.description.toString()
    }

    private fun offerPrice(postAdObj: PostAdModel) =
            (((postAdObj.ticketCount * postAdObj.price).toDouble()) -
                    ((postAdObj.ticketCount * postAdObj.price).toDouble() * (postAdObj.discount.toDouble() / 100))).toString()

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

    @Override
    fun onGenderChange(): CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            phonenumberVisible = View.VISIBLE
        } else {
            phonenumberVisible = View.GONE
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}


