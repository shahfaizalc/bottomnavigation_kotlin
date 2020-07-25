package com.guiado.akbhar.util

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guiado.akbhar.model.Quote
import com.guiado.akbhar.model.Quotes


class JsonToClassObject(internal var fragmentYearRecyclerView: Activity) {


    fun fetchChaptersInfoJsonData( fileString :String) : ArrayList<Quotes> {
        return  convertChaptersInfoJsonStringToClassObject(fileString);
    }

    private fun convertChaptersInfoJsonStringToClassObject(jsonString: String) : ArrayList<Quotes>{
        val listType = object : TypeToken<ArrayList<Quotes>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


}