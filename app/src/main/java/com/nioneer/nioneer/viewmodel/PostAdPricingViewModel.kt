package com.nioneer.nioneer.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.R
import com.nioneer.nioneer.model.PostAdModel
import com.nioneer.nioneer.util.MultipleClickHandler
import com.nioneer.nioneer.view.FragmentPostAdPricing

class PostAdPricingViewModel(activity: FragmentActivity, internal val fragmentProfileInfo: FragmentPostAdPricing)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {
        private val TAG = "ProfileGalleryViewModel"
    }

    @get:Bindable
    var price: String = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.price)
        }

    @get:Bindable
    var noOfTickets:String = ""
        set(noOfTickets) {
            field = noOfTickets
            notifyPropertyChanged(BR.noOfTickets)
        }

    @get:Bindable
    var discount: String? = null
        set(discount) {
            field = discount
            notifyPropertyChanged(BR.discount)
        }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {
        if(!(price.isEmpty()||noOfTickets.isEmpty())) {

            if (!handleMultipleClicks()) {
                Toast.makeText(fragmentProfileInfo.context,"Yet to implement", Toast.LENGTH_SHORT).show()

            }
        }else{
            Toast.makeText(fragmentProfileInfo.context,fragmentProfileInfo.context!!.resources.getText(R.string.mandatoryField), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
