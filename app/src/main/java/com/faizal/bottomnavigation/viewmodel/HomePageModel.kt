package com.faizal.bottomnavigation.viewmodel

import android.databinding.BaseObservable
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentHomePage
import com.faizal.bottomnavigation.view.*


class HomePageModel(activity: FragmentActivity, internal val fragmentProfileInfo: FragmentHomePage)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {

        private val TAG = "HomePageModel"
    }



}
