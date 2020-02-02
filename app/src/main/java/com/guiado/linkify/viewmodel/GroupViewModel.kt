package com.guiado.linkify.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.guiado.linkify.BR
import com.guiado.linkify.R
import com.guiado.linkify.adapter.GroupAdapter
import com.guiado.linkify.handler.NetworkChangeHandler
import com.guiado.linkify.listeners.EmptyResultListener
import com.guiado.linkify.model2.*
import com.guiado.linkify.network.FirbaseWriteHandler
import com.guiado.linkify.util.*
import com.guiado.linkify.view.FragmentGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class GroupViewModel(private val context: Context,
                     private val fragmentSignin: FragmentGroup,
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
    var userIds: MutableLiveData<List<Comments>> = MutableLiveData<List<Comments>>()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }


    @get:Bindable
    var review: String? = null
        set(city) {
            field = city
            notifyPropertyChanged(BR.review)
        }

    fun updateReview() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if (groups?.postedBy.isNullOrEmpty() || groups?.postedDate.isNullOrEmpty() || review.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val comments2 = getComment()

            if (groups?.comments.isNullOrEmpty()) {
                groups?.comments = ArrayList<Comments>()
                groups?.comments?.addAll(comments2)
                updateComment()
            } else {
                groups?.comments?.addAll(comments2)
                updateComment()
            }

        }
    }

    private fun updateComment() {
        FirbaseWriteHandler(fragmentSignin).updateGroups(groups!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess doDiscussionWrrite")
                getVal(groups?.comments)
                review = ""
            }
        })
    }

    private fun getComment(): ArrayList<Comments> {
        val comments = Comments()
        comments.commment = review ?: ""
        comments.commentedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        comments.commentedOn = System.currentTimeMillis().toString()
        comments.commentedUserName = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
        val comments2 = ArrayList<Comments>()
        comments2.add(comments)
        return comments2
    }

    var adapter = GroupAdapter()

    private fun readAutoFillItems() {
        val c = GenericValues()
        groups = c.getGroups(postAdObj, context)
        getVal(groups?.comments)

    }

    @get:Bindable
    var groups: Groups? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionCategories(groups!!.keyWords, context).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keyWordsTagg)
        }


    @get:Bindable
    var postedDate: String? = groups!!.postedDate?.toLong()?.let { convertLongToTime(it) }.toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.postedDate)
        }

    fun getVal(postedDat: ArrayList<Comments>?) {
        GlobalScope.launch(context = Dispatchers.Main) {
            while (userIds == null) {
            }
            userIds.value = postedDat
        }
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
