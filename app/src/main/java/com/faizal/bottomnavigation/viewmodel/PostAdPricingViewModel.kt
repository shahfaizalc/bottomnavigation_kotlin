package com.faizal.bottomnavigation.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.Toast
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentPostAdPricing

class PostAdPricingViewModel(activity: FragmentActivity, internal val fragmentProfileInfo: FragmentPostAdPricing, internal val postAdObj: PostAdModel)// To show list of user images (Gallery)
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
                val postAdModel = postAdObj
                postAdModel.price = price.toInt()
                postAdModel.discount = discount?. let { discount!!.toInt() } ?: let { 0 }
                postAdModel.ticketCount = noOfTickets.toInt()
//                val fragment = FragmentNewAddress()
//                val bundle = Bundle()
//                bundle.putParcelable(Constants.POSTAD_OBJECT, postAdModel)
//                fragment.setArguments(bundle)
//                fragmentProfileInfo.newInstance(1,fragment);

            }
        }else{
            Toast.makeText(fragmentProfileInfo.context,fragmentProfileInfo.context!!.resources.getText(R.string.mandatoryField), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
