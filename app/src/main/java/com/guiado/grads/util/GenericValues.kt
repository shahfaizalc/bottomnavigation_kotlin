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
