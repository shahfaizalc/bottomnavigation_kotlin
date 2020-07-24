package com.guiado.akbhar.viewmodel


import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import com.guiado.akbhar.activities.Main2Activity
import com.guiado.akbhar.model.LanguageRegionEnum
import com.guiado.akbhar.utils.Constants.LANGUAGE_ID
import com.guiado.akbhar.utils.SharedPreference
import com.guiado.akbhar.view.*


class LanguageModel(internal val activity: FragmentLanguage) // To show list of user images (Gallery)
    : BaseObservable() {


    companion object {
        private val TAG = "DiscussionModel"
    }

    @Override
    fun openNewsNArt() = View.OnClickListener() {
        launchMainActivity(LanguageRegionEnum.AR.name)
    }

    @Override
    fun openNewsFood() = View.OnClickListener() {
        launchMainActivity(LanguageRegionEnum.FR.name)
    }

    @Override
    fun openNewsTravel() = View.OnClickListener() {
        launchMainActivity(LanguageRegionEnum.EN.name)
    }

    private fun launchMainActivity( value :String) {
        val pref = SharedPreference(activity)
        pref.save(LANGUAGE_ID, value)
        activity.finish()
        val intentNext = Intent(activity, Main2Activity::class.java)
        activity.startActivity(intentNext)
    }

}



