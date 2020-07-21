package com.guiado.akbhar.viewmodel

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.akbhar.BR
import com.guiado.akbhar.R
import com.guiado.akbhar.listeners.EmptyResultListener
import com.guiado.akbhar.model.CoachItem
import com.guiado.akbhar.model2.PostDiscussion
import com.guiado.akbhar.util.*
import com.guiado.akbhar.view.FragmentGameChooser
import com.google.firebase.auth.FirebaseAuth
import com.guiado.akbhar.network.FirbaseWriteHandlerActivity
import java.util.*

class GameChooserModel(
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
        listOfCoachings = values.readDisuccsionTopics(fragmentGameChooser)
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
         keyWord.add(category )
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
                Toast.makeText(fragmentGameChooser, fragmentGameChooser.resources.getString(R.string.disussionSelect), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( postDiscussion.keyWords!!.size > 0 && postDiscussion.title!!.length > 3 ) {
                showProgresss(true)
                postDiscussion.searchTags = compareLIt().toList()

                postDiscussion.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                postDiscussion.postedDate = System.currentTimeMillis().toString()
                postDiscussion.postedByName = getUserName(fragmentGameChooser, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
                Log.d(TAG, "DocumentSnapshot  doDiscussionWrrite "  +postDiscussion.searchTags)
                val firbaseWriteHandler = FirbaseWriteHandlerActivity(fragmentGameChooser).updateDiscussion(postDiscussion, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                        Toast.makeText(fragmentGameChooser, fragmentGameChooser.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                        showProgresss(false)
                    }

                    override fun onSuccess() {
                        showProgresss(false)
                        Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                        fragmentGameChooser.finish();
                       // fragmentGameChooser.mFragmentNavigation.popFragment(2)
                       // fragmentGameChooser.mFragmentNavigation.replaceFragment(fragment);
                        showPopUpWindow()
                    }
                })
            }
        }
    }

    fun showPopUpWindow(){
        val view = getNotificationContentView(fragmentGameChooser,
                fragmentGameChooser.applicationContext.resources.getString(R.string.success_title),
                fragmentGameChooser.applicationContext.resources.getString(R.string.success_msg))
        val popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        view.findViewById<View>(R.id.closeBtn).setOnClickListener{
            popupWindow.dismiss()
        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}

