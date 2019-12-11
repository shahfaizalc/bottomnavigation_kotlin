package com.faizal.bottomnavigation.network

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.model2.PostEvents
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.model2.Reviews
import com.faizal.bottomnavigation.utils.Constants.BASEURL_COLLECTION_GEN_DISCUSSION
import com.faizal.bottomnavigation.utils.Constants.BASEURL_COLLECTION_GEN_POSTEVVENT
import com.faizal.bottomnavigation.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.faizal.bottomnavigation.utils.Constants.BASEURL_COLLECTION_GEN_REVIEW
import com.faizal.bottomnavigation.utils.Constants.BASEURL_COLLECTION_PROFILEPIC

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirbaseWriteHandler(private val fragmentBase: BaseFragment) {
    private val storageReference: StorageReference
    private val currentFirebaseUser: FirebaseUser?


    init {
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        val storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
    }

    fun localFileToFirebaseStorage(filePath: Uri, emptyResultListener: EmptyResultListener) {
        val ref = storageReference.child(BASEURL_COLLECTION_PROFILEPIC + currentFirebaseUser!!.uid)
        ref.putFile(filePath)
                .addOnSuccessListener {
                    Toast.makeText(fragmentBase.context, "Image successfull uploaded", Toast.LENGTH_LONG).show()
                    emptyResultListener.onSuccess()
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Toast.makeText(fragmentBase.context, "Failed to upload. Please try again later " + e.message, Toast.LENGTH_LONG).show()
                }

    }

    fun updateUserInfo(userInfo: Profile, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO)
        collection.document(currentFirebaseUser!!.uid).set(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateReview(userInfo: Reviews, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_REVIEW)
        collection.document(userInfo.date!!).set(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }



    fun updateEvents(userInfo: PostEvents, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_POSTEVVENT)
        collection.document(userInfo.postedDate!!).set(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateDiscussion(userInfo: PostDiscussion, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_DISCUSSION)
        collection.document(userInfo.postedDate!!).set(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun addComment(userInfo: PostDiscussion, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_DISCUSSION)
        collection.document(userInfo.postedDate!!).update("comments",userInfo.comments)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateUserInfoFollowed(userInfo: Profile, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO)
        collection.document(currentFirebaseUser!!.uid).update("followed",userInfo.followed)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateUserInfoFollowing(userInfo: Profile, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO)
        collection.document(currentFirebaseUser!!.uid).update("following",userInfo.following)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }



//    fun postAnAd(userInfo: PostAdModel, emptyResultListener: EmptyResultListener) {
//        val myDB = FirebaseFirestore.getInstance()
//        val docId = System.currentTimeMillis().toString()
//        val collection = myDB.collection(BASEURL_POST_AD)
//        collection.document(docId).set(userInfo)
//                .addOnSuccessListener {
//                    emptyResultListener.onSuccess()
//
//                    Log.d(TAG, "DocumentSnapshot added ")
//                }
//                .addOnFailureListener { e ->
//                    emptyResultListener.onFailure(e)
//                    Log.w(TAG, "Error in adding document", e)
//                }
//    }
//    fun postAnAdNews(userInfo: PostAdModel, emptyResultListener: EmptyResultListener) {
//        val myDB = FirebaseFirestore.getInstance()
//        val docId = System.currentTimeMillis().toString()
//        val collection = myDB.collection(BASEURL_POST_AD + currentFirebaseUser!!.uid)
//        collection.document(docId).set(userInfo)
//                .addOnSuccessListener {
//                    emptyResultListener.onSuccess()
//
//                    Log.d(TAG, "DocumentSnapshot added ")
//                }
//                .addOnFailureListener { e ->
//                    emptyResultListener.onFailure(e)
//                    Log.w(TAG, "Error in adding document", e)
//                }
//    }
    companion object {

        private val TAG = "FirbaseReadHandler"
    }

}