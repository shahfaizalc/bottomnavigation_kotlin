package com.guiado.grads.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.guiado.grads.R
import com.guiado.grads.model.*
import com.guiado.grads.model2.*
import com.guiado.grads.utils.Constants
import com.guiado.grads.utils.Constants.FILENAME_CATEGORY
import com.guiado.grads.utils.Constants.FILENAME_SINGLE_CHOICE_ATTR
import com.google.gson.Gson
import java.util.ArrayList
import java.util.LinkedHashMap

class GenericValues {

    fun readAutoFillItems(context: Context): ArrayList<IndiaItem> {
        val fileString = getFileString(Constants.FILENAME_CITIES, context)
        val gson = Gson()
        val staff = gson.fromJson(fileString, CitiesIndia::class.java)
        return staff.india as ArrayList<IndiaItem>
    }

    fun readCoachItems(context: Context): ArrayList<CoachItem> {
        val fileString = getFileString(Constants.FILENAME_COACH, context)
        val gson = Gson()
        val staff = gson.fromJson(fileString, Coaches::class.java)
        return staff.india as ArrayList<CoachItem>
    }

    fun readCourseCategory(context: Context): ArrayList<CoachItem> {
        val fileString = getFileString(Constants.FILENAME_COURSE_CATEGORY, context)
        val gson = Gson()
        val staff = gson.fromJson(fileString, Coaches::class.java)
        return staff.india as ArrayList<CoachItem>
    }



    fun readDisuccsionTopics(context: Context): ArrayList<CoachItem> {
        val fileString = getFileString(Constants.FILENAME_DISCUSSION, context)
        val gson = Gson()
        val staff = gson.fromJson(fileString, Coaches::class.java)
        return staff.india as ArrayList<CoachItem>
    }




    private fun getFileString(fileName: String, context: Context): String {
        return ReadAssetFile().readAssetFile(fileName, context)
    }


    fun discussionToString(profile : PostDiscussion) : String{
        val gson  = Gson();
        return gson.toJson(profile)
    }


    fun getDisccussion(fileName: String,context: Context): PostDiscussion {
        val gson = Gson()
        return gson.fromJson(fileName, PostDiscussion::class.java)
    }

    fun getGroups(fileName: String,context: Context): Groups {
        val gson = Gson()
        return gson.fromJson(fileName, Groups::class.java)
    }

    fun profileToString(profile : Profile) : String{
        val gson  = Gson();
        return gson.toJson(profile)
    }

    fun getProfile(fileName: String,context: Context): Profile {
        val gson = Gson()
        return gson.fromJson(fileName, Profile::class.java)
    }


}
