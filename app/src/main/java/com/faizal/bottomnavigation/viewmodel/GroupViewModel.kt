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
import com.faizal.bottomnavigation.listeners.UseInfoGeneralResultListener
import com.faizal.bottomnavigation.model2.*
import com.faizal.bottomnavigation.network.FirbaseReadHandler
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.*
import com.faizal.bottomnavigation.view.FragmentGroup
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

    @get:Bindable
    var likesState: Boolean? = isLiked()
        set(city) {
            field = city
            notifyPropertyChanged(BR.likesState)
        }

    @get:Bindable
    var bookmarkState: Boolean? = isBookmarked()
        set(city) {
            field = city
            notifyPropertyChanged(BR.bookmarkState)
        }


    @get:Bindable
    var sponsored: Boolean? = isFollowed()
        set(city) {
            field = city
            notifyPropertyChanged(BR.sponsored)
        }


    private fun isBookmarked(): Boolean? {

        var isFollow = false
        groups?.bookmarks.notNull {
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


    private fun isLiked(): Boolean? {

        var isFollow = false
        groups?.likes.notNull {
            val likes: MutableIterator<Likes> = it.iterator()
            while (likes.hasNext()) {
                val name = likes.next()
                if (name.likedBy.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
                    isFollow = true
                }
            }
        }

        return isFollow
    }

    private fun isFollowed(): Boolean? {

        var isFollow = false
        val user = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!);

        user.following.notNull {
            val it: MutableIterator<Follow> = it.iterator()
            while (it.hasNext()) {
                val name = it.next()
                if (name.userId.equals(groups?.postedBy)) {
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
            if (groups?.bookmarks.isNullOrEmpty()) {
                groups?.bookmarks = ArrayList<Bookmarks>()
            } else {
                val bookmarks: MutableIterator<Bookmarks> = groups?.bookmarks!!.iterator()
                while (bookmarks.hasNext()) {
                    val name = bookmarks.next()
                    if (name.markedById.equals(comments2.markedById)) {
                        isExist = true
                        comments2 = name
                    }
                }

            }

            if(isExist){
                groups?.bookmarks?.remove(comments2)
            } else {
                groups?.bookmarks?.add(comments2)
            }

            updateBookmmarks(isExist)

        }
    }

    private fun updateBookmmarks(exist: Boolean) {
        FirbaseWriteHandler(fragmentSignin).updateJoin(groups!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

                bookmarkState = !exist
//                getVal(postDiscussion?.comments)
//                review = ""
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





    fun updateLikes() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            var isExist = false
            var comments2 = getLikes()
            if (groups?.likes.isNullOrEmpty()) {
                groups?.likes = ArrayList<Likes>()
            } else {
                val likes: MutableIterator<Likes> = groups?.likes!!.iterator()
                while (likes.hasNext()) {
                    val name = likes.next()
                    if (name.likedBy.equals(comments2.likedBy)) {
                        isExist = true
                        comments2 = name
                    }
                }

            }

            if(isExist){
                groups?.likes?.remove(comments2)
            } else {
                groups?.likes?.add(comments2)
            }

            updatelike(isExist)

        }
    }

    private fun updatelike(exist: Boolean) {

    }

    private fun getLikes(): Likes {
        val comments = Likes()
        comments.likedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        comments.likedOn = System.currentTimeMillis().toString()
        comments.likedUserName = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
//        val comments2 = ArrayList<Likes>()
//        comments2.add(comments)
        return comments
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

    fun addFollowers() = View.OnClickListener {
        if (!handleMultipleClicks()) {

            var currentTime = System.currentTimeMillis().toString()

            var follow = Follow();
            follow.userId = groups!!.postedBy!!
            follow.fromDate = currentTime
            follow.userName = groups!!.postedByName ?: ""

            var isExist = false
            if (userProfile.following.isNullOrEmpty()) {
                userProfile.following = ArrayList<Follow>()
            } else {
                val it: MutableIterator<Follow> = userProfile.following!!.iterator()
                while (it.hasNext()) {
                    val name = it.next()
                    if (name.userId.equals(groups!!.postedBy)) {
                        isExist = true
                        follow = name

                    }
                }
            }

            if(isExist){
                userProfile.following?.remove(follow)
            } else {
                userProfile.following?.add(follow)
            }

            FirbaseWriteHandler(fragmentSignin).updateUserInfoFollowed(userProfile, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d("TAG", "DocumentSnapshot addFollowers onFailure " + e.message)
                    Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess() {

                    storeUserName(context, FirebaseAuth.getInstance().currentUser!!.uid, userProfile)
                    Log.d("TAG", "DocumentSnapshot onSuccess addFollowers")
                    getVal(groups?.comments)
                    review = ""
                    sponsored = !isExist

                    addFollowing(currentTime)

                }
            })
        }
    }

    private fun addFollowing(currentTime: String) {

        var follow = Follow();
        follow.userId = FirebaseAuth.getInstance().currentUser!!.uid
        follow.fromDate = currentTime
        follow.userName = userProfile.name ?: ""

        FirbaseReadHandler().getSepcificUserInfo(groups?.postedBy!! ,object : UseInfoGeneralResultListener {

            override fun onSuccess(profile1: Profile) {

                var isExist = false
                if (profile1.followers.isNullOrEmpty()) {
                    profile1.followers = ArrayList<Follow>()
                } else {
                    val it: MutableIterator<Follow> = profile1.followers!!.iterator()
                    while (it.hasNext()) {
                        val name = it.next()
                        if (name.userId.equals(follow.userId)) {
                            isExist = true
                            follow = name

                        }
                    }
                }

                if(isExist){
                    profile1.followers?.remove(follow)
                } else {
                    profile1.followers?.add(follow)
                }

                FirbaseWriteHandler(fragmentSignin).updateUserInfoFollowing(groups!!.postedBy!!,profile1, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d("TAG", "DocumentSnapshot addFollowing onFailure " + e.message)
                        Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess() {
                        Log.d("TAG", "DocumentSnapshot onSuccess addFollowing")
                    }
                })

            }

            override fun onFailure(e: Exception) {
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

    var adapter = CommentsAdapter()

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
    var keyWordsTagg: String? = getDiscussionKeys(groups!!.keyWords, context).toString()
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
