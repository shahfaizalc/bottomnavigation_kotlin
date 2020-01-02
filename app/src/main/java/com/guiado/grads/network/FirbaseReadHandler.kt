package com.guiado.grads.network

import android.content.Context
import android.util.Log
import android.util.NoSuchPropertyException
import com.guiado.grads.listeners.UseInfoGeneralResultListener
import com.guiado.grads.model2.Profile
import com.guiado.grads.util.firestoreSettings
import com.guiado.grads.util.storeUserName
import com.guiado.grads.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder


class FirbaseReadHandler {
    private val currentFirebaseUser: FirebaseUser?

    init {
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    }

    fun getCurrentUserInfo(useInfoGeneralResultListener: UseInfoGeneralResultListener) {
        getProfile(currentFirebaseUser!!.uid, useInfoGeneralResultListener)
    }

    fun getSepcificUserInfo(id:String , useInfoGeneralResultListener: UseInfoGeneralResultListener) {
        getProfile(id, useInfoGeneralResultListener)
    }

    private fun getProfile(id : String, useInfoGeneralResultListener: UseInfoGeneralResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        try {
            myDB.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)

        }
        val docRef = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO).document(id)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val gson = GsonBuilder().create()
                    val json = gson.toJson(document.data)

                    val userInfoGeneral = gson.fromJson<Profile>(json, Profile::class.java)
                    useInfoGeneralResultListener.onSuccess(userInfoGeneral)

                    Log.d(TAG, "getUserInfo success ")
                } else {
                    Log.d(TAG, "getUserInfo success : document not exist")
                    useInfoGeneralResultListener.onFailure(NoSuchPropertyException("Document not found"))
                }
            } else {
                Log.d(TAG, "getUserInfo failure ")
                useInfoGeneralResultListener.onFailure(task.exception!!)
            }
        }
    }



    fun storeUserNamePreference(context: Context) {

        val db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)

        }
        val query = db.collection("userinfo").document(currentFirebaseUser!!.uid);
        query.get()
                .addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                    if (task.isSuccessful) {
                        addTalentsItems(context,task)
                    } else {
                        Log.d("TAG", "Error getting documentss: " + task.exception!!.message)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d("TAG", "Failure getting documents: " + exception.localizedMessage) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d("TAG", "Success getting documents: " + valu) })
    }

    fun addTalentsItems(context: Context,document: Task<DocumentSnapshot>) {

        val posts  = document.result?.toObject(Profile::class.java)
        if(posts!=null)
            storeUserName(context, currentFirebaseUser!!.uid, posts )
    }

//    fun getMarkers(param: StringListListener) {
//        val myDB = FirebaseFirestore.getInstance()
//        myDB.collection(Constants.BASEURL_POST_AD + currentFirebaseUser!!.uid).
//                get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
//            if (task.isSuccessful) {
//                val list = ArrayList<String>()
//                for (document in task.result!!) {
//                    list.add(document.id)
//                }
//                Log.d(TAG, list.toString())
//                param.onSuccess(list)
//            } else {
//                Log.d(TAG, "Error getting documents: ", task.exception)
//                param.onFailure(task.exception!!)
//            }
//        })
//    }
//
//
//    fun getMyAds(strList: ArrayList<String>, adsResultListener: AdsResultListener) {
//        var postAdModel = ArrayList<Profile>()
//        var idsSize = strList.size
//
//        for (ids: String in strList) {
//
//            val myDB = FirebaseFirestore.getInstance()
//            val docs = myDB.collection(Constants.BASEURL_COLLECTION_GEN_PROFILEINFO + currentFirebaseUser!!.uid)
//            val docRef = docs.document(ids)
//
//            docRef.get().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val document = task.result
//                    if (document!!.exists()) {
//                        val gson = GsonBuilder().create()
//                        val json = gson.toJson(document.data)
//
//
//                        val userInfoGeneral = gson.fromJson<PostAdModel>(json, PostAdModel::class.java as Type)
//                        postAdModel.add(userInfoGeneral)
//                        if(idsSize.equals(postAdModel.size)){
//                            adsResultListener.onSuccess(postAdModel)
//                        }
//
//                        Log.d(TAG, "getUserInfo success ")
//                    } else {
//                        Log.d(TAG, "getUserInfo success : document not exist")
//                        adsResultListener.onFailure(NoSuchPropertyException("Document not found"))
//                    }
//                } else {
//                    Log.d(TAG, "getUserInfo failure ")
//                    adsResultListener.onFailure(task.exception!!)
//
//                }
//            }
//        }
//    }
    companion object {

        private val TAG = "FirbaseReadHandler"
    }
}