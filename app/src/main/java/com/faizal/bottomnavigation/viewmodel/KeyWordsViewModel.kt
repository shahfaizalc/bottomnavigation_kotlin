package com.faizal.bottomnavigation.viewmodel

import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.Events.MyCustomEvent
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentKeyWords
import org.greenrobot.eventbus.EventBus
import java.util.*

class KeyWordsViewModel(internal val activity: FragmentActivity,
                        internal val fragmentProfileInfo: FragmentKeyWords,
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


    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.readCourseCategory(activity.applicationContext)
    }

    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }

    @get:Bindable
    var listOfCoachingsSelected: MutableList<Int>? =  if (profile.keyWords!=null) profile.keyWords else mutableListOf<Int>()
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.listOfCoachingsSelected)
        }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {

        if (!handleMultipleClicks()) {
            activity.onBackPressed();
            profile.keyWords = listOfCoachingsSelected

            EventBus.getDefault().post(MyCustomEvent(profile));
        } else {
            Toast.makeText(fragmentProfileInfo.context,
                    fragmentProfileInfo.context!!.resources.getText(R.string.mandatoryField),
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}
