package com.guiado.grads.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.guiado.grads.BR
import com.guiado.grads.Events.MyCustomEvent
import com.guiado.grads.R
import com.guiado.grads.model.Address
import com.guiado.grads.model.IndiaItem
import com.guiado.grads.model2.Profile
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.view.FragmentAddress
import org.greenrobot.eventbus.EventBus
import java.util.*

class AdressViewModel( internal val fragmentProfileInfo: FragmentAddress,
                      internal val postAdObj: String)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {
        private val TAG = "ProfileGalleryViewModel"
    }

    var profile = Profile()

    init {
        readAutoFillItems()
        profile = GenericValues().getProfile(postAdObj, fragmentProfileInfo)
    }

    var cityCode: String? = profile.address?.cityCode

    private fun readAutoFillItems() {
        val c = GenericValues()
        roleAdapterAddress = c.readAutoFillItems(fragmentProfileInfo)

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

                fragmentProfileInfo.finish()
                EventBus.getDefault().post(MyCustomEvent(profile));

            }
        } else {
            Toast.makeText(fragmentProfileInfo, fragmentProfileInfo.resources.getText(R.string.mandatoryField), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}