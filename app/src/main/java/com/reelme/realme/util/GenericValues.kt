package com.reelme.realme.util

import android.content.Context
import com.reelme.realme.model2.*
import com.google.gson.Gson

class GenericValues {












    private fun getFileString(fileName: String, context: Context): String {
        return ReadAssetFile().readAssetFile(fileName, context)
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
