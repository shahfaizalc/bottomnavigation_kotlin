package com.faizal.bottomnavigation.viewmodel

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.IndiaItem
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants.POSTAD_OBJECT
import com.faizal.bottomnavigation.view.FragmentAddress
import com.faizal.bottomnavigation.view.FragmentRequestComplete
import java.util.*

class AdressViewModel(internal val activity: FragmentActivity, internal val fragmentProfileInfo: FragmentAddress, internal val postAdObj: PostAdModel)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {
        private val TAG = "ProfileGalleryViewModel"
    }

    init {
        readAutoFillItems()
    }

    var cityCode: String? = "0"


    @get:Bindable
    var city: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.city)
        }


    private fun readAutoFillItems() {
        val c = GenericValues()
        roleAdapterAddress = c.readAutoFillItems(activity.applicationContext)
    }

    @get:Bindable
    var roleAdapterAddress: ArrayList<IndiaItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    @get:Bindable
    var street: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.street)
        }

    @get:Bindable
    var landmark: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.landmark)
        }

    @get:Bindable
    var town: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.town)

        }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if (!( street.isEmpty()|| landmark.isEmpty()|| town.isEmpty())) {

            val address = Address();
            address.locationname = landmark;
            address.streetName = street
            address.town = town
            address.cityCode = cityCode

            postAdObj.address = address

            if (!handleMultipleClicks()) {
                val postAdModel = postAdObj
                postAdModel.address

                val fragment = FragmentRequestComplete()
                val bundle = Bundle()
                bundle.putParcelable(POSTAD_OBJECT, postAdModel)
                fragment.setArguments(bundle)
                fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

            }
        } else {
            Toast.makeText(fragmentProfileInfo.context, fragmentProfileInfo.context!!.resources.getText(R.string.mandatoryField), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
