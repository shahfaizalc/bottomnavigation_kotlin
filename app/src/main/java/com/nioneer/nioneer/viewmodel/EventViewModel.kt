package com.nioneer.nioneer.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.R
import com.nioneer.nioneer.handler.NetworkChangeHandler
import com.nioneer.nioneer.listeners.EmptyResultListener
import com.nioneer.nioneer.model2.*
import com.nioneer.nioneer.util.*
import com.nioneer.nioneer.view.*
import com.google.firebase.auth.FirebaseAuth
import com.nioneer.nioneer.network.FirbaseWriteHandlerActivity

class EventViewModel(
                     private val fragmentSignin: FragmentEvent,
                     internal val postAdObj: String) : BaseObservable(),
        NetworkChangeHandler.NetworkChangeListener {

    private val TAG = "RequestComplete  "

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    var userProfile = Profile()


    init {
        networkHandler()
        readAutoFillItems()
        userProfile = getUserName(fragmentSignin, FirebaseAuth.getInstance().currentUser!!.uid);
    }

    @get:Bindable
    var review: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.review)
        }


    @get:Bindable
    var bookmarkState: Boolean? = isBookmarked()
        set(city) {
            field = city
            notifyPropertyChanged(BR.bookmarkState)
        }


    private fun isBookmarked(): Boolean? {

        var isFollow = false
        postDiscussion?.members.notNull {
            val bookmarks: MutableIterator<Bookmarks> = it.iterator()
            while (bookmarks.hasNext()) {
                val name = bookmarks.next()
                if (name.markedById.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
                    isFollow = true
                }
            }
        }

        return isFollow
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




    fun updateBookmarks() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            showProgresss(true)
            var isExist = false
            var comments2 = getbookmarks()
            if (postDiscussion?.members.isNullOrEmpty()) {
                postDiscussion?.members = ArrayList<Bookmarks>()
                postDiscussion?.bookmarkBy = ArrayList<String>()
            } else {
                val bookmarks: MutableIterator<Bookmarks> = postDiscussion?.members!!.iterator()
                while (bookmarks.hasNext()) {
                    val name = bookmarks.next()
                    if (name.markedById.equals(comments2.markedById)) {
                        isExist = true
                        comments2 = name
                    }
                }

            }

            if(isExist){
                postDiscussion?.members?.remove(comments2)
                postDiscussion?.bookmarkBy?.remove(comments2.markedById)
            } else {
                postDiscussion?.members?.add(comments2)
                postDiscussion?.bookmarkBy?.add(comments2.markedById)
            }

            updateBookmmarks(isExist)

        }
    }

    private fun updateBookmmarks(exist: Boolean) {
        FirbaseWriteHandlerActivity(fragmentSignin).updateEvents(postDiscussion!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                showProgresss(false)
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")
                showProgresss(false)

                bookmarkState = !exist

                Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
//                val fragment = FragmentMyGroups()
//                val bundle = Bundle()
//                fragment.setArguments(bundle)
//                fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

            }
        })
    }

    private fun getbookmarks(): Bookmarks {
        val comments = Bookmarks()
        comments.markedById = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        comments.markedOn = System.currentTimeMillis().toString()
        comments.markedByUser = getUserName(fragmentSignin, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
//        val comments2 = ArrayList<Likes>()
//        comments2.add(comments)
        return comments
    }




    private fun readAutoFillItems() {
        val c = GenericValues()
        postDiscussion = c.getEventss(postAdObj, fragmentSignin)

    }

    @get:Bindable
    var postDiscussion: Events? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionCategories(postDiscussion!!.keyWords, fragmentSignin).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keyWordsTagg)
        }

    @get:Bindable
    var location: String? = postDiscussion!!.address?.locationname +" "+postDiscussion!!.address?.streetName +" "+postDiscussion!!.address?.town +" "+postDiscussion!!.address?.city
        set(price) {
            field = price
            notifyPropertyChanged(BR.location)
        }


    @get:Bindable
    var postedDate: String? = postDiscussion!!.postedDate?.toLong()?.let { convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.postedDate)
        }

    @get:Bindable
    var adDate: String? = postDiscussion!!.startDate?.toLong()?.let { convertLongToTime(it) }.toString()+postDiscussion!!.endDate?.toLong()?.let {" - "+ convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.adDate)
        }



    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(fragmentSignin)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(fragmentSignin)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(id), Toast.LENGTH_LONG).show()
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}