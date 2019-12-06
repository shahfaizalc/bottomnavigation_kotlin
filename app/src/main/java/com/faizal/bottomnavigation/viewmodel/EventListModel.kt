package com.faizal.bottomnavigation.viewmodel

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentEventList
import com.faizal.bottomnavigation.view.FragmentGameChooser
import com.faizal.bottomnavigation.view.FragmentPostAd

class EventListModel(internal val fragmentGameChooser: FragmentEventList) : BaseObservable() {

    @get:Bindable
    var userIds: List<String> = emptyList()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }

    init {
        addValues()
    }

    private fun addValues() {
        userIds = listOf("Archery", "Badminton", "Basketball", "Baseball",
                "Boxing", "Cricket", "Football", "Golf", "Gymnastics", "Hockey",
                "Judo", "Kabaddi", "Karate", "Music", "Rugby", "Racing",
                "Table Tennis", "Tennis", "Volleyball", "Wrestling", "Others")
    }


    fun onNextButtonClick(category: Int) {

        if (!handleMultipleClicks()) {
            val postAdModel = PostAdModel();
            postAdModel.categorySelect = category
            val fragment = FragmentPostAd()
            val bundle = Bundle()
            bundle.putParcelable(Constants.POSTAD_OBJECT, postAdModel)
            fragment.setArguments(bundle)
            fragmentGameChooser.mFragmentNavigation.pushFragment(fragmentGameChooser.newInstance(2, fragment, bundle));
        }

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}