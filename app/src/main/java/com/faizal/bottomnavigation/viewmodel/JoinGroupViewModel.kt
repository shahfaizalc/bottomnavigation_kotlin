package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.listeners.UseInfoGeneralResultListener
import com.faizal.bottomnavigation.model2.*
import com.faizal.bottomnavigation.network.FirbaseReadHandler
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.*
import com.faizal.bottomnavigation.view.FragmentJoinGroup
import com.faizal.bottomnavigation.view.FragmentMyGroups
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class JoinGroupViewModel(private val context: Context,
                         private val fragmentSignin: FragmentJoinGroup,
                         internal val postAdObj: String) : BaseObservable(),
        NetworkChangeHandler.NetworkChangeListener {

    private val TAG = "RequestComplete  "

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    var userProfile = Profile()


    init {
        networkHandler()
        readAutoFillItems()
        userProfile = getUserName(context, FirebaseAuth.getInstance().currentUser!!.uid);
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




    fun updateBookmarks() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            var isExist = false
            var comments2 = getbookmarks()
            if (postDiscussion?.members.isNullOrEmpty()) {
                postDiscussion?.members = ArrayList<Bookmarks>()
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
            } else {
                postDiscussion?.members?.add(comments2)
            }

            updateBookmmarks(isExist)

        }
    }

    private fun updateBookmmarks(exist: Boolean) {
        FirbaseWriteHandler(fragmentSignin).updateJoin(postDiscussion!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

                bookmarkState = !exist

                Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                val fragment = FragmentMyGroups()
                val bundle = Bundle()
                fragment.setArguments(bundle)
                fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

            }
        })
    }

    private fun getbookmarks(): Bookmarks {
        val comments = Bookmarks()
        comments.markedById = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        comments.markedOn = System.currentTimeMillis().toString()
        comments.markedByUser = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
//        val comments2 = ArrayList<Likes>()
//        comments2.add(comments)
        return comments
    }




    private fun readAutoFillItems() {
        val c = GenericValues()
        postDiscussion = c.getGroups(postAdObj, context)

    }

    @get:Bindable
    var postDiscussion: Groups? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionKeys(postDiscussion!!.keyWords, context).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keyWordsTagg)
        }


    @get:Bindable
    var postedDate: String? = postDiscussion!!.postedDate?.toLong()?.let { convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.postedDate)
        }


    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(context)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(context)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

}