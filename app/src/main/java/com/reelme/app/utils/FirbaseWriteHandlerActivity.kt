package com.reelme.app.utils

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.*
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_PROFILEPIC
import com.reelme.app.listeners.StringResultListener
import java.io.ByteArrayOutputStream


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
                    Toast.makeText(fragmentBase, "Image successfully uploaded", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
                    emptyResultListener.onSuccess()
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Toast.makeText(fragmentBase, "Failed to upload. Please try again later " + e.message, Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
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


    fun coompressjpeg(path: Uri?, param: StringResultListener){

        val progressDialog = ProgressDialog(fragmentBase)
        progressDialog.setTitle("Uploading")
        progressDialog.show()

        val storage = FirebaseStorage.getInstance()

        // Create a storage reference from our app

        val storageReference = storage.reference;
        val filename ="userfiles/"+System.currentTimeMillis()+".jpg";

        val mountainImagesRef = storageReference.child(filename)

        val bmp = MediaStore.Images.Media.getBitmap(fragmentBase.contentResolver, path);
        val baos =  ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);

        val data = baos.toByteArray();


        var uploadTask = mountainImagesRef.putBytes(data)

        uploadTask.addOnSuccessListener {

            Toast.makeText(fragmentBase, "Upload successful", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
            progressDialog.dismiss()
            it.metadata!!.reference!!.downloadUrl.toString()
            param.onSuccess(it.metadata!!.reference!!.downloadUrl.toString())


        }.addOnFailureListener {
            Log.d("Uploading", "" + it)
            Toast.makeText(fragmentBase, "Upload Failed -> $it", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
            progressDialog.dismiss()
            param.onFailure(it)


        }.addOnProgressListener { taskSnapshot ->
            //calculating progress percentage
            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount

            //displaying percentage in progress dialog
            progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
        };
    }


    fun uploadToStorage(path: Uri?, param: StringResultListener) {

        val filePath = path
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            val progressDialog = ProgressDialog(fragmentBase)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            var filename ="userfiles/"+System.currentTimeMillis()+".jpg";
          var filepath = "gs://reelme-bf98e.appspot.com/"+filename


            val storage = FirebaseStorage.getInstance()

            // Create a storage reference from our app

            val storageReference = storage.reference;

            val riversRef = storageReference.child(filename)
            riversRef.putFile(filePath)
                    .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                        //if the upload is successfull
                        //hiding the progress dialog
                        progressDialog.dismiss()


                       var ksk = it.metadata as StorageMetadata


                        param.onSuccess(filepath)

                        //and displaying a success toast
                        Toast.makeText(fragmentBase, "File Uploaded ", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
                    })
                    .addOnFailureListener(OnFailureListener { exception ->
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss()

                        param.onFailure(exception)

                        Log.d("Uploading",""+exception)
                        Log.d("Uploading",""+exception.stackTrace)

                        //and displaying error message
                        Toast.makeText(fragmentBase, exception.message, Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
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