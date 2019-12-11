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
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.util.convertLongToTime
import com.faizal.bottomnavigation.util.getDiscussionKeys
import com.faizal.bottomnavigation.view.FragmentOneDiscussion
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder


class OneDiscussionViewModel(private val context: Context, private val fragmentSignin: FragmentOneDiscussion, internal val postAdObj: String) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    companion object {
        private val TAG = "RequestComplete  "
    }

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    private var comments : Comments
    private var comments2 : ArrayList<Comments>


    init {
        networkHandler()
        comments = Comments()
        comments2 = ArrayList<Comments>()

        readAutoFillItems()


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
            comments.commment = review ?: ""
            notifyPropertyChanged(BR.review)
        }

    fun updateReview() = View.OnClickListener {


        if (!handleMultipleClicks()) {
            if (listOfCoachings?.postedBy.isNullOrEmpty() || listOfCoachings?.postedDate.isNullOrEmpty() || review.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            comments.commentedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            comments.commentedOn = System.currentTimeMillis().toString()
            comments2.add(comments)

            listOfCoachings?.comments = comments2

            val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).addComment(listOfCoachings!!, object : EmptyResultListener {
                override fun onFailure(e: Exception) {
                    Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                    Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                }

                override fun onSuccess() {
                    Log.d("TAG", "DocumentSnapshot onSuccess doDiscussionWrrite")
//                        val fragment = FragmentProfile()
//                        val bundle = Bundle()
//                        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//                        fragment.setArguments(bundle)
//                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

                }
            })

        }


    }

    var adapter = CommentsAdapter()

    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.getDisccussion(postAdObj, context)

        getVal(listOfCoachings?.postedDate)


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

    fun getVal(postedDat: String?) {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("discussion").document(postedDat!!)

        query.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val listOfRating = emptyList<Comments>().toMutableList()


                    val document = task.result
                    if (document!!.exists()) {
                        val gson = GsonBuilder().create()
                        val json = gson.toJson(document.data)

                        val userInfoGeneral = gson.fromJson<PostDiscussion>(json, PostDiscussion::class.java )
                        Log.d(TAG, "sucess getting saanu: " +userInfoGeneral.comments)

                        val iterator: Iterator<Comments> = userInfoGeneral.comments!!.iterator()

                        while (iterator.hasNext()) {
                            Log.d(TAG, "sucess getting saanu: " )
                            listOfRating.add(iterator.next())
                        }
                        userIds.value = listOfRating


                        Log.d(TAG, "getUserInfo success ")
                    } else {
                        Log.d(TAG, "getUserInfo success : document not exist")
                    }

                } else {
                    Log.d(TAG, "Error getting saanu: " + task.exception!!.message)
                }

        }.addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Success getting documents: " + valu) })
    }


//    fun findClickded() {
//        Log.d("tag", "taggg")
//        val fragment = FragmentProfileEdit()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
//    }
//
//    fun logout() {
//        FirebaseAuth.getInstance().signOut();
//        val fragment = FragmentWelcome()
//        Log.d("tag", "logout")
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.replaceFragment(fragmentSignin.newInstance(0,fragment,bundle));
//        fragmentSignin.mFragmentNavigation.viewBottom(View.GONE)
//    }

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
