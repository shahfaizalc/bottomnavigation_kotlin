package com.guiado.grads.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.InverseMethod
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.listeners.UseInfoGeneralResultListener
import com.guiado.grads.model2.*
import com.guiado.grads.network.FirbaseReadHandler
import com.guiado.grads.network.FirbaseWriteHandler
import com.guiado.grads.network.FirbaseWriteHandlerActivity
import com.guiado.grads.util.*
import com.guiado.grads.view.FirestoreDisccussFragmment


class ActivityDiscussionViewModel(private val context: Context,
                                  private val fragmentSignin: FirestoreDisccussFragmment,
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
    var showUserOptions: Int? = getUiState()
        set(city) {
            field = city
            notifyPropertyChanged(BR.showUserOptions)
        }

    private fun getUiState(): Int? {
        return if(postDiscussion?.postedBy.equals(FirebaseAuth.getInstance().currentUser!!.uid)) View.INVISIBLE else View.VISIBLE
    }


    @get:Bindable
    var likesState: Boolean? = isLiked()
        set(city) {
            field = city
            notifyPropertyChanged(BR.likesState)
        }


    @get:Bindable
    var likesCount: Int? = getLikeCount()
        set(city) {
            field = city
            notifyPropertyChanged(BR.likesCount)
        }

    @InverseMethod("convertIntToString")
    open fun convertStringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
    }

    fun convertIntToString(value: Int): String? {
        return value.toString()
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
        postDiscussion?.bookmarks.notNull {
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

    private fun getLikeCount(): Int? {
         var count = 0
         postDiscussion?.likes.notNull {
            count = postDiscussion?.likes!!.size
        }
        return count
    }


    private fun isLiked(): Boolean? {

        var isFollow = false
        postDiscussion?.likes.notNull {
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
                if (name.userId.equals(postDiscussion?.postedBy)) {
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
            if (postDiscussion?.bookmarks.isNullOrEmpty()) {
                postDiscussion?.bookmarks = ArrayList<Bookmarks>()
                postDiscussion?.bookmarkBy = ArrayList<String>()
            } else {
                val bookmarks: MutableIterator<Bookmarks> = postDiscussion?.bookmarks!!.iterator()
                while (bookmarks.hasNext()) {
                    val name = bookmarks.next()
                    if (name.markedById.equals(comments2.markedById)) {
                        isExist = true
                        comments2 = name
                    }
                }

            }

            if(isExist){
                postDiscussion?.bookmarks?.remove(comments2)
                postDiscussion?.bookmarkBy?.remove(comments2.markedById)
            } else {
                postDiscussion?.bookmarks?.add(comments2)
                postDiscussion?.bookmarkBy?.add(comments2.markedById)

            }

            updateBookmmarks(isExist)

        }
    }

    private fun updateBookmmarks(exist: Boolean) {
        FirbaseWriteHandlerActivity(fragmentSignin).updateLikes(postDiscussion!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
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
            if (postDiscussion?.likes.isNullOrEmpty()) {
                postDiscussion?.likes = ArrayList<Likes>()
            } else {
                val likes: MutableIterator<Likes> = postDiscussion?.likes!!.iterator()
                while (likes.hasNext()) {
                    val name = likes.next()
                    if (name.likedBy.equals(comments2.likedBy)) {
                        isExist = true
                        comments2 = name
                    }
                }

            }

            if(isExist){
                postDiscussion?.likes?.remove(comments2)
            } else {
                postDiscussion?.likes?.add(comments2)
            }

            updatelike(isExist)

        }
    }

    private fun updatelike(exist: Boolean) {
        FirbaseWriteHandlerActivity(fragmentSignin).updateLikes(postDiscussion!!, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

                likesState = !exist

                if(exist){
                    likesCount =likesCount!!- 1
                } else {
                    likesCount =likesCount!!+ 1
                }
//                getVal(postDiscussion?.comments)
//                review = ""
            }
        })
    }

    private fun getLikes(): Likes {
        val likes = Likes()
        likes.likedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        likes.likedOn = System.currentTimeMillis().toString()
        likes.likedUserName = getUserName(context, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
//        val comments2 = ArrayList<Likes>()
//        comments2.add(comments)
        return likes
    }




    fun addFollowers() = View.OnClickListener {
        if (!handleMultipleClicks()) {

            var currentTime = System.currentTimeMillis().toString()

            var follow = Follow();
            follow.userId = postDiscussion!!.postedBy!!
            follow.fromDate = currentTime
            follow.userName = postDiscussion!!.postedByName ?: ""

            var isExist = false
            if (userProfile.following.isNullOrEmpty()) {
                userProfile.following = ArrayList<Follow>()
            } else {
                val it: MutableIterator<Follow> = userProfile.following!!.iterator()
                while (it.hasNext()) {
                    val name = it.next()
                    if (name.userId.equals(postDiscussion!!.postedBy)) {
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

            FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfoFollowed(userProfile, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d("TAG", "DocumentSnapshot addFollowers onFailure " + e.message)
                    Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess() {

                    storeUserName(context, FirebaseAuth.getInstance().currentUser!!.uid, userProfile)
                    Log.d("TAG", "DocumentSnapshot onSuccess addFollowers")
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

        FirbaseReadHandler().getSepcificUserInfo(postDiscussion?.postedBy!! ,object : UseInfoGeneralResultListener {

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

                FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfoFollowing(postDiscussion!!.postedBy!!,profile1, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d("TAG", "DocumentSnapshot addFollowing onFailure " + e.message)
                        Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
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


    private fun readAutoFillItems() {
        val c = GenericValues()
        postDiscussion = c.getDisccussion(postAdObj, context)

    }

    @get:Bindable
    var postDiscussion: PostDiscussion? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var keyWordsTagg: String? = getDiscussionCategories(postDiscussion!!.keyWords, context).toString()
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
