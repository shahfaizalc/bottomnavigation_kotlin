package com.guiado.koodal.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.koodal.BR
import com.guiado.koodal.R
import com.guiado.koodal.handler.NetworkChangeHandler
import com.guiado.koodal.listeners.EmptyResultListener
import com.guiado.koodal.model2.*
import com.guiado.koodal.network.FirbaseWriteHandler
import com.guiado.koodal.util.*
import com.guiado.koodal.view.*
import com.google.firebase.auth.FirebaseAuth
import com.guiado.koodal.model.EventStatus

class MyEventViewModel(private val context: Context,
                     private val fragmentSignin: FragmentMyEvent,
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
        events?.members.notNull {
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

     fun deleteBookmmarks() = View.OnClickListener {
         events!!.eventState = EventStatus.DELETED
        FirbaseWriteHandler(fragmentSignin).updateEvents(events!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

                Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                val fragment = FragmentMyEvents()
                fragmentSignin.mFragmentNavigation.popFragment(1);
                fragmentSignin.mFragmentNavigation.replaceFragment(fragment);
            }
        })
    }


    fun hideBookmmarks() = View.OnClickListener {

        events!!.eventState = if(events!!.eventState.equals(EventStatus.HIDDEN) ) EventStatus.SHOWING else EventStatus.HIDDEN

        FirbaseWriteHandler(fragmentSignin).updateEvents(events!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

                Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                val fragment = FragmentMyEvents()
                fragmentSignin.mFragmentNavigation.popFragment(1);
                fragmentSignin.mFragmentNavigation.replaceFragment(fragment);
            }
        })
    }

    fun updateBookmarks() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            var isExist = false
            var comments2 = getbookmarks()
            if (events?.members.isNullOrEmpty()) {
                events?.members = ArrayList<Bookmarks>()
                events?.bookmarkBy = ArrayList<String>()
            } else {
                val bookmarks: MutableIterator<Bookmarks> = events?.members!!.iterator()
                while (bookmarks.hasNext()) {
                    val name = bookmarks.next()
                    if (name.markedById.equals(comments2.markedById)) {
                        isExist = true
                        comments2 = name
                    }
                }

            }

            if(isExist){
                events?.members?.remove(comments2)
                events?.bookmarkBy?.remove(comments2.markedById)
            } else {
                events?.members?.add(comments2)
                events?.bookmarkBy?.add(comments2.markedById)
            }

            updateBookmmarks(isExist)

        }
    }

    private fun updateBookmmarks(exist: Boolean) {
        FirbaseWriteHandler(fragmentSignin).updateEvents(events!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

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
        comments.markedByUser = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
//        val comments2 = ArrayList<Likes>()
//        comments2.add(comments)
        return comments
    }




    private fun readAutoFillItems() {
        val c = GenericValues()
        events = c.getEventss(postAdObj, context)

    }

    @get:Bindable
    var events: Events? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionCategories(events!!.keyWords, context).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keyWordsTagg)
        }

    @get:Bindable
    var location: String? = events!!.address?.locationname +" "+events!!.address?.streetName +" "+events!!.address?.town +" "+events!!.address?.city
        set(price) {
            field = price
            notifyPropertyChanged(BR.location)
        }


    @get:Bindable
    var postedDate: String? = events!!.postedDate?.toLong()?.let { convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.postedDate)
        }

    @get:Bindable
    var adDate: String? = events!!.startDate?.toLong()?.let { convertLongToTime(it) }.toString()+events!!.endDate?.toLong()?.let {" - "+ convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.adDate)
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