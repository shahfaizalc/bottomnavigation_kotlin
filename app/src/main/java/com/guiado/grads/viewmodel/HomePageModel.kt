package com.guiado.grads.viewmodel

import androidx.databinding.BaseObservable
import androidx.fragment.app.FragmentActivity
import com.guiado.grads.view.FragmentHomePage


class HomePageModel(activity: FragmentActivity, internal val fragmentProfileInfo: FragmentHomePage)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {

        private val TAG = "HomePageModel"
    }



}
