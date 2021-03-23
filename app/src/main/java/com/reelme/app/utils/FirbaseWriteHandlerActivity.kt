package com.reelme.app.utils

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_PROFILEPIC

class FirbaseWriteHandlerActivity(private val fragmentBase: Activity) {
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
                    Toast.makeText(fragmentBase, "Image successfull uploaded", Toast.LENGTH_LONG).show()
                    emptyResultListener.onSuccess()
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Toast.makeText(fragmentBase, "Failed to upload. Please try again later " + e.message, Toast.LENGTH_LONG).show()
                }

    }

    fun updateUserInfo(userInfo: UserModel, emptyResultListener: EmptyResultListener) {
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

    companion object {

        private val TAG = "FirbaseReadHandler"
    }

}