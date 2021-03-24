package com.reelme.app.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_PROFILEPIC
import com.google.firebase.storage.OnProgressListener


class FirbaseWriteHandlerActivity(private val fragmentBase: Activity) {
    private val storageReference: StorageReference
    private val currentFirebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    init {
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

    fun uploadToStorage(path: Uri?) {

        val filePath = path
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            val progressDialog = ProgressDialog(fragmentBase)
            progressDialog.setTitle("Uploading")
            progressDialog.show()


            val storage = FirebaseStorage.getInstance()

            // Create a storage reference from our app

            val storageReference = storage.getReference();
            val riversRef = storageReference.child("userfiles/"+System.currentTimeMillis()+".jpg")
            riversRef.putFile(filePath)
                    .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                        //if the upload is successfull
                        //hiding the progress dialog
                        progressDialog.dismiss()

                        //and displaying a success toast
                        Toast.makeText(fragmentBase, "File Uploaded ", Toast.LENGTH_LONG).show()
                    })
                    .addOnFailureListener(OnFailureListener { exception ->
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss()


                        Log.d("Uploading",""+exception)
                        Log.d("Uploading",""+exception.stackTrace)

                        //and displaying error message
                        Toast.makeText(fragmentBase, exception.message, Toast.LENGTH_LONG).show()
                    })
                    .addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        //calculating progress percentage
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    })
        }
    }






    companion object {

        private val TAG = "FirbaseReadHandler"
    }

}