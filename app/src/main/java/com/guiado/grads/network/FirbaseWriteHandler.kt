package com.guiado.grads.network

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.model2.*
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_DISCUSSION
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_FEEDBACK
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_GROUPS
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_POSTEVVENT
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_REVIEW
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_PROFILEPIC

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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



    fun updatepostEvents(userInfo: PostEvents, emptyResultListener: EmptyResultListener) {
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

    fun updateGroups(discussion: Groups, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_GROUPS)
        collection.document(discussion.postedDate!!).set(discussion)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()
                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateEvents(discussion: Events, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_POSTEVVENT)
        collection.document(discussion.postedDate!!).set(discussion)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot added ")
                    emptyResultListener.onSuccess()
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }



    fun updateDiscussion(discussion: PostDiscussion, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_DISCUSSION)
        collection.document(discussion.postedDate!!).set(discussion)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()
                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateFeedback(discussion: Feedback, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_FEEDBACK)
        collection.document(discussion.feedbackOn).set(discussion)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()
                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }



    fun deleteDiscussion(discussion: PostDiscussion, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_DISCUSSION)
        collection.document(discussion.postedDate!!).delete()
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()
                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateLikes(userInfo: PostDiscussion, emptyResultListener: EmptyResultListener) {
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

    fun updateJoin(userInfo: Groups, emptyResultListener: EmptyResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_GROUPS)
        collection.document(userInfo.postedDate!!).set(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "updateJoin DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "updateJoin Error in adding document", e)
                }.addOnCompleteListener {
                    Log.d(TAG, "updateJoin DocumentSnapshot complete ")

                }
    }


    fun updateUserInfoFollowed(userInfo: Profile, emptyResultListener: EmptyResultListener) {
        Log.d(TAG, "DocumentSnapshot added ID Current "+currentFirebaseUser!!.uid)

        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO)
        collection.document(currentFirebaseUser.uid).update("following",userInfo.following)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun updateUserInfoFollowing(parentid:String, userInfo: Profile, emptyResultListener: EmptyResultListener) {
        Log.d(TAG, "DocumentSnapshot added ID Currentid "+parentid)


        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO)
        collection.document(parentid).update("followers",userInfo.followers)
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