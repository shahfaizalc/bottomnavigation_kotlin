package com.reelme.realme.util

import android.content.Context
import com.reelme.realme.model2.Profile
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun <T : Any> T?.notNull(function: (it: T) -> Unit) {
    if (this != null) function(this)
}


fun getUserName(context: Context, id:String) :Profile  {
    val sharedPreference =  context.getSharedPreferences("USER",Context.MODE_PRIVATE)
    var userProfile = sharedPreference.getString(id,"")
     if(userProfile.isNullOrEmpty()){return Profile()
     }
    return GenericValues().getProfile(userProfile,context)
}



fun String.onDatePickerClick( ) : Long {

    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val date = formatter.parse(this) as Date
    return date.time
}


 fun String.sentenceToWords(): List<String> {
    val list1 = ArrayList<String>()
    val p: Pattern = Pattern.compile("[a-zA-Z]+")

    val m1: Matcher = p.matcher(this.toLowerCase(Locale.getDefault()))

    while (m1.find()) {

        list1.add(m1.group());
    }
    return list1.toSet().toList();
}

