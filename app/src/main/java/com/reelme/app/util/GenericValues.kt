package com.reelme.app.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reelme.app.pojos.*
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
}
