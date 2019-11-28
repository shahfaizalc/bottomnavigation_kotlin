package com.faizal.bottomnavigation.network

import android.util.Log
import android.util.NoSuchPropertyException
import com.faizal.bottomnavigation.listeners.UseInfoGeneralResultListener
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.utils.Constants.BASEURL_COLLECTION_GEN_PROFILEINFO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import java.lang.reflect.Type


class FirbaseReadHandler {
    private val currentFirebaseUser: FirebaseUser?

    init {
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    }

    fun getUserInfo(useInfoGeneralResultListener: UseInfoGeneralResultListener) {
        val myDB = FirebaseFirestore.getInstance()
        val docRef = myDB.collection(BASEURL_COLLECTION_GEN_PROFILEINFO).document(currentFirebaseUser!!.uid)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val gson = GsonBuilder().create()
                    val json = gson.toJson(document.data)

                    val userInfoGeneral = gson.fromJson<Profile>(json, Profile::class.java )
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