package com.faizal.guiado.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.guiado.BR
import com.faizal.guiado.Events.MyCustomEvent
import com.faizal.guiado.R
import com.faizal.guiado.model.Address
import com.faizal.guiado.model.IndiaItem
import com.faizal.guiado.model2.Profile
import com.faizal.guiado.util.GenericValues
import com.faizal.guiado.util.MultipleClickHandler
import com.faizal.guiado.view.FragmentAddress
import org.greenrobot.eventbus.EventBus
import java.util.*

class AdressViewModel(internal val activity: FragmentActivity, internal val fragmentProfileInfo: FragmentAddress,
                      internal val postAdObj: String)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {
        private val TAG = "ProfileGalleryViewModel"
    }

    var profile = Profile()

    init {
        readAutoFillItems()
        profile = GenericValues().getProfile(postAdObj, activity.applicationContext)
    }

    var cityCode: String? = "0"

    private fun readAutoFillItems() {
        val c = GenericValues()
        roleAdapterAddress = c.readAutoFillItems(activity.applicationContext)

    }

    @get:Bindable
    var city: String? = profile.address?.city
        set(city) {
            field = city
            notifyPropertyChanged(BR.city)
        }

    @get:Bindable
    var roleAdapterAddress: ArrayList<IndiaItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }



    @get:Bindable
    var street: String? = profile.address?.streetName
        set(price) {
            field = price
            notifyPropertyChanged(BR.street)
        }

    @get:Bindable
    var landmark: String? = profile.address?.locationname
        set(price) {
            field = price
            notifyPropertyChanged(BR.landmark)
        }

    @get:Bindable
    var town: String? = profile.address?.town
        set(price) {
            field = price
            notifyPropertyChanged(BR.town)

        }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {

        if (!(street.isNullOrEmpty() || landmark.isNullOrEmpty()  || town.isNullOrEmpty() )) {

            val address = Address();
            address.locationname = landmark;
            address.streetName = street
            address.town = town
            address.cityCode = cityCode
            address.city = city
            profile.address = address


            if (!handleMultipleClicks()) {

                activity.onBackPressed();
                EventBus.getDefault().post(MyCustomEvent(profile));

            }
        } else {
            Toast.makeText(fragmentProfileInfo.context, fragmentProfileInfo.context!!.resources.getText(R.string.mandatoryField), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
