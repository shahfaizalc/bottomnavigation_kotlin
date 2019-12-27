package com.guiado.grads.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.model.PostAdModel
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.view.FragmentPostAdPricing

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
