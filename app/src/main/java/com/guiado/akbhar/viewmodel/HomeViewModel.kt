package com.guiado.akbhar.viewmodel

import androidx.databinding.BaseObservable
import androidx.fragment.app.FragmentActivity
import com.guiado.akbhar.view.FragmentHomePage
import com.guiado.akbhar.view.HomeFragment

class HomeViewModel(activity: FragmentActivity, internal val fragmentProfileInfo: HomeFragment)// To show list of user images (Gallery)
    : BaseObservable() {
    companion object {

        private val TAG = "HomePageModel"
    }



}
