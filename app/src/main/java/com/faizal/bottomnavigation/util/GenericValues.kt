package com.faizal.bottomnavigation.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.*
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.model2.PostEvents
import com.faizal.bottomnavigation.model2.Profile
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.utils.Constants.FILENAME_CATEGORY
import com.faizal.bottomnavigation.utils.Constants.FILENAME_SINGLE_CHOICE_ATTR
import com.google.gson.Gson
import java.util.ArrayList
import java.util.LinkedHashMap

class GenericValues {

    fun getProfileIcons(context: Context): LinkedHashMap<String, Drawable> {
        val aboutProfile = LinkedHashMap<String, Drawable>()
        aboutProfile["Profession"] = context.resources.getDrawable(R.drawable.profession, null)
        aboutProfile["Height"] = context.resources.getDrawable(R.drawable.height, null)
        aboutProfile["Religion"] = context.resources.getDrawable(R.drawable.religion, null)
        aboutProfile["Ethnic"] = context.resources.getDrawable(R.drawable.ethnic, null)
        return aboutProfile
    }

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

    fun readDisuccsionTopics(context: Context): ArrayList<CoachItem> {
        val fileString = getFileString(Constants.FILENAME_DISCUSSION, context)
        val gson = Gson()
        val staff = gson.fromJson(fileString, Coaches::class.java)
        return staff.india as ArrayList<CoachItem>
    }

    fun readSingleAttribute(context: Context): SingleAttribute {
        val fileString = getFileString(FILENAME_SINGLE_CHOICE_ATTR, context)
        val gson = Gson()
        return gson.fromJson(fileString, SingleAttribute::class.java)
    }

    fun readCategory(context: Context): Category {
        val fileString = getFileString(FILENAME_CATEGORY, context)
        val gson = Gson()
        return gson.fromJson(fileString, Category::class.java)
    }
    private fun getFileString(fileName: String, context: Context): String {
        return ReadAssetFile().readAssetFile(fileName, context)
    }

      fun profileToString(profile : Profile) : String{
        val gson  = Gson();
       return gson.toJson(profile)
    }

    fun eventsToString(profile : PostEvents) : String{
        val gson  = Gson();
        return gson.toJson(profile)
    }
    fun getEvents(fileName: String,context: Context): PostEvents {
        val gson = Gson()
        return gson.fromJson(fileName, PostEvents::class.java)
    }

    fun discussionToString(profile : PostDiscussion) : String{
        val gson  = Gson();
        return gson.toJson(profile)
    }
    fun getDisccussion(fileName: String,context: Context): PostDiscussion {
        val gson = Gson()
        return gson.fromJson(fileName, PostDiscussion::class.java)
    }

    fun getProfile(fileName: String,context: Context): Profile {
        val gson = Gson()
        return gson.fromJson(fileName, Profile::class.java)
    }

    fun addressToString(profile : Address) : String{
        val gson  = Gson();
        return gson.toJson(profile)
    }
    fun getAddress(fileName: String,context: Context): Address {
        val gson = Gson()
        return gson.fromJson(fileName, Address::class.java)
    }
}
