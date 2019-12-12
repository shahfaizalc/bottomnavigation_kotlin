package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.adapter.CommentsAdapter
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.model2.Comments
import com.faizal.bottomnavigation.model2.Follow
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.*
import com.faizal.bottomnavigation.view.FragmentOneDiscussion
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OneDiscussionViewModel(private val context: Context,
                             private val fragmentSignin: FragmentOneDiscussion,
                             internal val postAdObj: String) : BaseObservable(),
        NetworkChangeHandler.NetworkChangeListener {

    private val TAG = "RequestComplete  "

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false
    var str = Profile()


    init {
        networkHandler()
        readAutoFillItems()
        str = getUserName(context, FirebaseAuth.getInstance().currentUser!!.uid);
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

    @get:Bindable
    var sponsored: Boolean? = false
        set(city) {
            field = city
            notifyPropertyChanged(BR.sponsored)
        }

    fun updateReview() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if (listOfCoachings?.postedBy.isNullOrEmpty() || listOfCoachings?.postedDate.isNullOrEmpty() || review.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val comments2 = getComment()

            if (listOfCoachings?.comments.isNullOrEmpty()) {
                listOfCoachings?.comments = ArrayList<Comments>()
                listOfCoachings?.comments?.addAll(comments2)
                updateComment()
            } else {
                listOfCoachings?.comments?.addAll(comments2)
                updateComment()
            }

        }
    }

    private fun updateComment() {
        FirbaseWriteHandler(fragmentSignin).updateDiscussion(listOfCoachings!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess doDiscussionWrrite")
                getVal(listOfCoachings?.comments)
                review = ""
            }
        })
    }

//    private fun addcomment() {
//         FirbaseWriteHandler(fragmentSignin).addComment(listOfCoachings!!, object : EmptyResultListener {
//            override fun onFailure(e: Exception) {
//                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
//                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onSuccess() {
//                Log.d("TAG", "DocumentSnapshot onSuccess doDiscussionWrrite")
//                getVal(listOfCoachings?.comments)
//                review = ""
//            }
//        })
//    }


    fun addFollowers() = View.OnClickListener {
        if (!handleMultipleClicks()) {

            var follw = Follow();
            follw.followedId = listOfCoachings!!.postedBy!!
            follw.followedOn = System.currentTimeMillis().toString()
            follw.followedBy = listOfCoachings!!.postedByName ?: ""

            var isExist = false
            if (str.followed.isNullOrEmpty()) {
                str.followed = ArrayList<Follow>()
            } else {
                val it: MutableIterator<Follow> = str.followed!!.iterator()
                while (it.hasNext()) {
                    val name = it.next()
                    if (name.followedId.equals(listOfCoachings!!.postedBy)) {
                        isExist = true
                        follw = name

                    }
                }
            }

            if(isExist){
                str.followed?.remove(follw)
            } else {
                str.followed?.add(follw)
            }

            FirbaseWriteHandler(fragmentSignin).updateUserInfoFollowed(str, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                    Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess() {

                    storeUserName(context, FirebaseAuth.getInstance().currentUser!!.uid, str)
                    Log.d("TAG", "DocumentSnapshot onSuccess doDiscussionWrrite")
                    getVal(listOfCoachings?.comments)
                    review = ""
                    sponsored = isExist

                }
            })
        }
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

    var adapter = CommentsAdapter()

    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.getDisccussion(postAdObj, context)
        getVal(listOfCoachings?.comments)

    }

    @get:Bindable
    var listOfCoachings: PostDiscussion? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionKeys(listOfCoachings!!.keyWords, context).toString()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keyWordsTagg)
        }


    @get:Bindable
    var postedDate: String? = listOfCoachings!!.postedDate?.toLong()?.let { convertLongToTime(it) }.toString()
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
