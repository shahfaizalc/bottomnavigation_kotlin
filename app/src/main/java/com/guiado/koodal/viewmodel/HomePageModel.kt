package com.guiado.koodal.viewmodel

import androidx.databinding.BaseObservable
import androidx.fragment.app.FragmentActivity
import com.guiado.koodal.view.FragmentHomePage


class HomePageModel(activity: FragmentActivity, internal val fragmentProfileInfo: FragmentHomePage)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {

        private val TAG = "HomePageModel"
    }



}
