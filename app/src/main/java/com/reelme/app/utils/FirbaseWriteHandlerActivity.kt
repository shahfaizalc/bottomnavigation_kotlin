package com.reelme.app.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.*
import com.google.gson.GsonBuilder
import com.reelme.app.listeners.*
import com.reelme.app.model2.AdventuresTopics
import com.reelme.app.model2.BonusTopics
import com.reelme.app.model2.SkipTopics
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_PROFILEPIC
import com.reelme.app.utils.Constants.BASEURL_COLLECTION_TOPICS_SKIP
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
                    Toast.makeText(fragmentBase, "Image successfully uploaded", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                    emptyResultListener.onSuccess()
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Toast.makeText(fragmentBase, "Failed to upload. Please try again later " + e.message, Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                }

    }


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    fun updateSkipTopics(userInfo: SkipTopics, emptyResultListener: EmptyResultListener) {

        if (!isNetworkAvailable(fragmentBase)) {

            Toast.makeText(fragmentBase, "You appear to be offline. Please check your internet settings.", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 50); show() }
            var e = Exception()
            emptyResultListener.onFailure(e)
        }

        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_TOPICS_SKIP)
        collection.document(userInfo.topicId+currentFirebaseUser!!.uid).set(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }



    fun updateUserInfo(userInfo: UserModel, emptyResultListener: EmptyResultListener) {

        if (!isNetworkAvailable(fragmentBase)) {

            Toast.makeText(fragmentBase, "You appear to be offline. Please check your internet settings.", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 50); show() }
            var e = Exception()
            emptyResultListener.onFailure(e)
        }

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

    fun updateUserInfoitems(userInfo: Map<String, String?>, emptyResultListener: EmptyResultListener) {

        if (!isNetworkAvailable(fragmentBase)) {

            Toast.makeText(fragmentBase, "You appear to be offline. Please check your internet settings.", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 50); show() }
            var e = Exception()

            emptyResultListener.onFailure(e)

        }

        val myDB = FirebaseFirestore.getInstance()
        val collection = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO)
        collection.document(currentFirebaseUser!!.uid).update(userInfo)
                .addOnSuccessListener {
                    emptyResultListener.onSuccess()

                    Log.d(TAG, "DocumentSnapshot added ")
                }
                .addOnFailureListener { e ->
                    emptyResultListener.onFailure(e)
                    Log.w(TAG, "Error in adding document", e)
                }
    }

    fun coompressjpeg(path: Uri?, param: StringResultListener) {

        val progressDialog = ProgressDialog(fragmentBase)
        progressDialog.setTitle("Uploading")
        progressDialog.show()

        val storage = FirebaseStorage.getInstance()

        // Create a storage reference from our app

        val storageReference = storage.reference;
        val filename = "userfiles/" + System.currentTimeMillis() + ".jpg";

        val mountainImagesRef = storageReference.child(filename)

        val bmp = MediaStore.Images.Media.getBitmap(fragmentBase.contentResolver, path);
        val baos = ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);

        val data = baos.toByteArray();


        var uploadTask = mountainImagesRef.putBytes(data)

        uploadTask.addOnSuccessListener {

            Toast.makeText(fragmentBase, "Upload successful", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
            progressDialog.dismiss()

            val downUrl: Task<Uri> = it.metadata!!.reference!!.downloadUrl
            while (!downUrl.isComplete) {
                Log.i("Uploading:", "isNotComplete")
            }
            Log.d("Uploading:isComplete><><", downUrl.result.toString())

            param.onSuccess(downUrl.result.toString())

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
        }.addOnCompleteListener {


        }
    }


    fun uploadToStorage(path: Uri?, param: StringResultListener) {

        val filePath = path
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            val progressDialog = ProgressDialog(fragmentBase)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            var filename = "userfiles/" + System.currentTimeMillis() + ".jpg";
            var filepath = "gs://reelme-bf98e.appspot.com/" + filename


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
                        Toast.makeText(fragmentBase, "File Uploaded ", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                    })
                    .addOnFailureListener(OnFailureListener { exception ->
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss()

                        param.onFailure(exception)

                        Log.d("Uploading", "" + exception)
                        Log.d("Uploading", "" + exception.stackTrace)

                        //and displaying error message
                        Toast.makeText(fragmentBase, exception.message, Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                    })
                    .addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        //calculating progress percentage
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    })
        }
    }


    fun doGetEvents(username: String, param: StringResultListener) {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("users");
        query.whereEqualTo("username", username)//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {

                        for (document in task.result!!) {

                            Log.d(TAG, "Successful getting documentss: " + document.data)

                        }


                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception ->
                    Log.d(TAG, "Failure getting documents: " + exception.localizedMessage)
                    param.onFailure(exception)

                })
                .addOnSuccessListener(OnSuccessListener { valu ->
                    Log.d(TAG, "Success getting documents: " + valu.documents.size)
                    param.onSuccess("" + valu.documents.size)
                })
    }


    fun doGetBonusTopics(param: BonusTopicsResultListener) {

        var bonusTopics: ArrayList<BonusTopics> = ArrayList<BonusTopics>()

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("bonusTopics");
        query.whereEqualTo("enabled", true)//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {

                        for (document in task.result!!) {

                            Log.d(TAG, "Successful getting documentss: " + document.data)

                            val gson = GsonBuilder().create()
                            val json = gson.toJson(document.data)

                            val userInfoGeneral = gson.fromJson<BonusTopics>(json, BonusTopics::class.java)
                            Log.d("Faizal", userInfoGeneral.topicDescription);

                            bonusTopics.add(userInfoGeneral)
                        }


                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception ->
                    Log.d(TAG, "Failure getting documents: " + exception.localizedMessage)
                    param.onFailure(exception)

                })
                .addOnSuccessListener { valu ->
                    Log.d(TAG, "Success getting" + bonusTopics.size + " documents: " + valu.documents.size)
                    param.onSuccess(bonusTopics)
                }
    }


    fun doGetAdventureTopics(param: AdventureTopicsResultListener) {

        var bonusTopics: ArrayList<AdventuresTopics> = ArrayList<AdventuresTopics>()

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("adventureTopics");
        query.whereEqualTo("enabled", true)//.whereEqualTo("enabled", true)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {

                        for (document in task.result!!) {

                            Log.d(TAG, "Successful getting documentss: " +document.id+" "+ document.data)

                            val gson = GsonBuilder().create()
                            val json = gson.toJson(document.data)

                            val userInfoGeneral = gson.fromJson<AdventuresTopics>(json, AdventuresTopics::class.java)
                            Log.d("Faizal", userInfoGeneral.topicDescription);

                            bonusTopics.add(userInfoGeneral)
                        }


                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception ->
                    Log.d(TAG, "Failure getting documents: " + exception.localizedMessage)
                    param.onFailure(exception)

                })
                .addOnSuccessListener { valu ->
                    Log.d(TAG, "Success getting" + bonusTopics.size + " documents: " + valu.documents.size)
                    param.onSuccess(bonusTopics)
                }
    }

    fun doGetSkipTopics(param: SkipTopicsResultListener) {

        var bonusTopics: ArrayList<SkipTopics> = ArrayList<SkipTopics>()

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("topicsSkip");
        query.whereEqualTo("uid", currentFirebaseUser!!.uid)//.whereEqualTo("showDate", showDate)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    val any = if (task.isSuccessful) {

                        for (document in task.result!!) {


                            Log.d(TAG, "Successful getting documentss: " +document.id+" "+ document.data)

                            val gson = GsonBuilder().create()
                            val json = gson.toJson(document.data)

                            val userInfoGeneral = gson.fromJson<SkipTopics>(json, SkipTopics::class.java)
                            Log.d("skip","topic"+ userInfoGeneral.topicId);

                            bonusTopics.add(userInfoGeneral)
                        }


                    } else {
                        Log.d(TAG, "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception ->
                    Log.d(TAG, "Failure getting documents: " + exception.localizedMessage)
                    param.onFailure(exception)

                })
                .addOnSuccessListener { valu ->
                    Log.d(TAG, "Success getting" + bonusTopics.size + " documents: " + valu.documents.size)
                    param.onSuccess(bonusTopics)
                }
    }

    companion object {

        private val TAG = "FirbaseReadHandler"
    }

}