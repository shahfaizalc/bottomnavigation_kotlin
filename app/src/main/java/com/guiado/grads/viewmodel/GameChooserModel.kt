package com.guiado.grads.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.model.CoachItem
import com.guiado.grads.model2.PostDiscussion
import com.guiado.grads.network.FirbaseWriteHandler
import com.guiado.grads.util.*
import com.guiado.grads.view.FragmentDiscussions
import com.guiado.grads.view.FragmentGameChooser
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class GameChooserModel(internal val activity: FragmentActivity,
                       internal val fragmentGameChooser: FragmentGameChooser,
                       internal val postAdObj: String?) : BaseObservable() {

    private val TAG = "GameChooserModel"

    var postDiscussion = PostDiscussion()
    var keyWord: MutableList<Int>

    init {
        postDiscussion = PostDiscussion()
        keyWord = mutableListOf()
        readAutoFillItems()

    }

    private fun readAutoFillItems() {
        val values = GenericValues()
        listOfCoachings = values.readDisuccsionTopics(activity.applicationContext)
        postDiscussion.title = postAdObj
    }

    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }

    @get:Bindable
    var showSignUpProgress: Int = View.INVISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpProgress)
        }

    @get:Bindable
    var showSignUpBtn: Int = View.VISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpBtn)
        }

    fun showProgresss(isShow : Boolean){
        if(isShow){
            showSignUpBtn = View.INVISIBLE
            showSignUpProgress = View.VISIBLE }
        else{
            showSignUpBtn = View.VISIBLE
            showSignUpProgress = View.INVISIBLE
        }
    }

    fun onNextButtonClick(category: Int) {
         keyWord.clear()
         keyWord.add(category + 1)
         postDiscussion.keyWords = keyWord

    }

    private fun compareLIt(): List<String> {
        val list1 = postDiscussion.title!!.sentenceToWords()
        Log.d("list2","indian" + list1)
        return list1
    }


    fun doDiscussionWrrite() = View.OnClickListener {


        if (!handleMultipleClicks()) {
            if ( postDiscussion.keyWords.isNullOrEmpty() || postDiscussion.title.isNullOrEmpty() ) {
                Toast.makeText(fragmentGameChooser.context, fragmentGameChooser.context!!.resources.getString(R.string.disussionSelect), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( postDiscussion.keyWords!!.size > 0 && postDiscussion.title!!.length > 3 ) {
                showProgresss(true)
                postDiscussion.searchTags = compareLIt().toList()

                postDiscussion.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                postDiscussion.postedDate = System.currentTimeMillis().toString()
                postDiscussion.postedByName = getUserName(activity.applicationContext, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
                Log.d(TAG, "DocumentSnapshot  doDiscussionWrrite "  +postDiscussion.searchTags)
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentGameChooser).updateDiscussion(postDiscussion, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                        Toast.makeText(fragmentGameChooser.context, fragmentGameChooser.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                        showProgresss(false)
                    }

                    override fun onSuccess() {
                        showProgresss(false)
                        Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                        val fragment = FragmentDiscussions()
                        val bundle = Bundle()
                        fragment.setArguments(bundle)
                        fragmentGameChooser.mFragmentNavigation.replaceFragment(fragment);

                    }
                })
            }
        }

    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}