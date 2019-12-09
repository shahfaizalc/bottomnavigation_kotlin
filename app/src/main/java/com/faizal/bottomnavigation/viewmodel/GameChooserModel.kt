package com.faizal.bottomnavigation.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.Events.MyCustomEvent
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentGameChooser
import com.faizal.bottomnavigation.view.FragmentPostAd
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

class GameChooserModel(internal val activity: FragmentActivity,
                       internal val fragmentGameChooser: FragmentGameChooser) : BaseObservable() {

    var profile = Profile()
     var keyWord: MutableList<Int>

    init {
        readAutoFillItems()
        profile = Profile()
        keyWord = mutableListOf()

    }
    private fun readAutoFillItems() {
        val values = GenericValues()
        listOfCoachings = values.readDisuccsionTopics(activity.applicationContext)
    }

    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    fun onNextButtonClick(category: Int) {

            if (!handleMultipleClicks()) {
                activity.onBackPressed();
                keyWord.add(category)
                profile.keyWords = keyWord

                EventBus.getDefault().post(MyCustomEvent(profile));
            } else {
                Toast.makeText(fragmentGameChooser.context,
                        fragmentGameChooser.context!!.resources.getText(R.string.mandatoryField),
                        Toast.LENGTH_SHORT).show()
            }

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}