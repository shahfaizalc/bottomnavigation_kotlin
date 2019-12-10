package com.faizal.bottomnavigation.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.view.FragmentGameChooser
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class GameChooserModel(internal val activity: FragmentActivity,
                       internal val fragmentGameChooser: FragmentGameChooser,
                       internal val postAdObj: String?) : BaseObservable() {

    private val TAG = "GameChooserModel"

    var profile = PostDiscussion()
    var keyWord: MutableList<Int>

    init {
        profile = PostDiscussion()
        keyWord = mutableListOf()
        readAutoFillItems()

    }

    private fun readAutoFillItems() {
        val values = GenericValues()
        listOfCoachings = values.readDisuccsionTopics(activity.applicationContext)
        profile.title = postAdObj
    }

    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    fun onNextButtonClick(category: Int) {

        if (!handleMultipleClicks()) {
            keyWord.clear()

            Toast.makeText(fragmentGameChooser.context,""+ profile.title,
                    Toast.LENGTH_SHORT).show()
            keyWord.add(category + 1)
            profile.keyWords = keyWord
        } else {
            Toast.makeText(fragmentGameChooser.context,
                    fragmentGameChooser.context!!.resources.getText(R.string.mandatoryField),
                    Toast.LENGTH_SHORT).show()
        }

    }


    fun doDiscussionWrrite() = View.OnClickListener {


        if (!handleMultipleClicks()) {
            if ( profile.keyWords.isNullOrEmpty() || profile.title.isNullOrEmpty() ) {
                Toast.makeText(fragmentGameChooser.context, fragmentGameChooser.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( profile.keyWords!!.size > 0 && profile.title!!.length > 3 ) {
                profile.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                profile.postedDate = System.currentTimeMillis().toString()
                Log.d(TAG, "DocumentSnapshot  doDiscussionWrrite "  )
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentGameChooser).updateDiscussion(profile, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                        Toast.makeText(fragmentGameChooser.context, fragmentGameChooser.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
//                        val fragment = FragmentProfile()
//                        val bundle = Bundle()
//                        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//                        fragment.setArguments(bundle)
//                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

                    }
                })
            }
        }

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}