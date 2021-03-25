package com.reelme.app.util

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reelme.app.listeners.UseInfoGeneralResultListener
import com.reelme.app.pojos.*
import com.reelme.app.view.*
import java.util.*

class GenericValues {

    fun getFileString(fileName: String, context: Context): String {
        return ReadAssetFile().readAssetFile(fileName, context)
    }

    fun genderToString(profile: Gender) : String{
        val gson  = Gson();
        return gson.toJson(profile)
    }

//    fun getGender(values: String, context: Context): List<Array<Gender>> {
//
//       val lists = listOf(Gson().fromJson(values, Array<Gender>::class.java))
//        return  lists
//    }

     fun getGender(jsonString: String, context: Context) : ArrayList<GenderList>{
        val listType = object : TypeToken<ArrayList<GenderList>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun getRelationshipStatusList(jsonString: String, context: Context) : ArrayList<RelationshipStatusList>{
        val listType = object : TypeToken<ArrayList<RelationshipStatusList>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


    fun getChildListStatusList(jsonString: String, context: Context) : ArrayList<ChildList>{
        val listType = object : TypeToken<ArrayList<ChildList>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


    fun getRelegionList(jsonString: String, context: Context) : ArrayList<RelegionList>{
        val listType = object : TypeToken<ArrayList<RelegionList>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun getOccupationsList(jsonString: String, context: Context) : ArrayList<OccupationList>{
        val listType = object : TypeToken<ArrayList<OccupationList>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun getHobbyList(jsonString: String, context: Context) : ArrayList<HobbyList>{
        val listType = object : TypeToken<ArrayList<HobbyList>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


    fun isUserProfileComplete(fragment: FragmentWelcome, param: UseInfoGeneralResultListener) {

        FirbaseReadHandler().getCurrentUserInfo(object : UseInfoGeneralResultListener{
            override fun onSuccess(userInfoGeneral: UserModel) {

                Log.d("GenericValues", "getCurrentUserInfo succcess");

                val gsonValue = Gson().toJson(userInfoGeneral)
                    val sharedPreference =  fragment.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putString("USER_INFO",gsonValue)
                    editor.apply()
                param.onSuccess(userInfoGeneral)
                navigateToNext(fragment)
            }

            override fun onFailure(e: Exception) {
                Log.d("GenericValues", "getCurrentUserInfo exception");
                param.onFailure(e)

                navigateToNext(fragment)
            }
        })
    }


    fun navigateToNext(fragment: FragmentWelcome) {

        val sharedPreference = fragment.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        var userModel = UserModel()

        try {
            userModel = Gson().fromJson(coronaJson, UserModel::class.java)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception$e");
            fragment.startActivity(Intent(fragment, FragmentEnterMobile::class.java));
        }

        userModel.notNull {
            if (userModel.phoneNumber.isEmpty()){
                fragment.startActivity(Intent(fragment, FragmentEnterMobile::class.java));
            } else
//            if (userModel.skipReferalCode == true){
//                fragment.startActivity(Intent(fragment, FragmentReferralMobile::class.java));
//            } else
                if (userModel.emailId.isNullOrEmpty()){
                    fragment.startActivity(Intent(fragment, FragmentEmailAddress::class.java));
                } else
                    if (userModel.firstName.isNullOrEmpty()){
                        fragment.startActivity(Intent(fragment, FragmentFullNameMobile::class.java));
                    } else
                        if (userModel.secondName.isNullOrEmpty()){
                            fragment.startActivity(Intent(fragment, FragmentFullNameMobile::class.java));
                        } else
                            if (userModel.dob.isNullOrEmpty()){
                                fragment.startActivity(Intent(fragment, FragmentDate::class.java));
                            } else
                                if (userModel.skipUsername == true){
                                    fragment.startActivity(Intent(fragment, FragmentUserName::class.java));
                                } else
                                    if (!userModel.profilePic.isNullOrEmpty()){
                                        fragment.startActivity(Intent(fragment, FragmentImgSelction::class.java));
                                    } else
                                        if (userModel.skipBio == true){
                                            fragment.startActivity(Intent(fragment, FragmentBioMobile::class.java));
                                        } else
                                            if (userModel.skipGender == true){
                                                fragment.startActivity(Intent(fragment, FlightsActivity::class.java));
                                            }  else
                                                if (userModel.skipRelationshipStatus == true){
                                                    fragment.startActivity(Intent(fragment, FragmentEnterMobile::class.java));
                                                } else
                                                    if (userModel.skipChildren == true){
                                                        fragment.startActivity(Intent(fragment, ChildrenActivity::class.java));
                                                    } else
                                                        if (userModel.skipOccupation == true){
                                                            fragment.startActivity(Intent(fragment, FragmentOccupation::class.java));
                                                        } else
                                                            if (userModel.skipReligiousBeliefs == true){
                                                                fragment.startActivity(Intent(fragment, RelegionActivity::class.java));
                                                            } else
                                                                if (userModel.skipHobbiesAndInterest == true){
                                                                    fragment.startActivity(Intent(fragment, FragmentHobbies::class.java));
                                                                }else {
                                                                        fragment.startActivity(Intent(fragment, FragmentHomePage::class.java));
                                                                    }
        }
    }


    private fun getUserInfo(context: FragmentWelcome): UserModel {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        return try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception$e");
            null!!
        }
    }

}
