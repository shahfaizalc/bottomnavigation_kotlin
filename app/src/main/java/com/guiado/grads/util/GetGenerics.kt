package com.guiado.grads.util

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.guiado.grads.R
import com.guiado.grads.model.Address
import com.guiado.grads.model.CoachItem
import com.guiado.grads.model2.Profile
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


//fun offerPrice(postAdObj: Profile) =
//        (((postAdObj.ticketCount * postAdObj.price).toDouble()) -
//                ((postAdObj.ticketCount * postAdObj.price).toDouble() * (postAdObj.discount.toDouble() / 100))).toString()

fun getAddress(address: Address?) = address!!.locationname +
        ", " + address.streetName + ", " + address.town + ", " + address.city + ", " + address.state


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd MMM yyyy")
    return format.format(date)
}

fun <T : Any> T?.notNull(function: (it: T) -> Unit) {
    if (this != null) function(this)
}

fun storeUserName(context: Context, id:String, name: Profile){
    val sharedPreference =  context.getSharedPreferences("USER",Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(id,GenericValues().profileToString(name))
    editor.apply()
}

fun getUserName(context: Context, id:String) :Profile  {
    val sharedPreference =  context.getSharedPreferences("USER",Context.MODE_PRIVATE)
    var userProfile = sharedPreference.getString(id,"")
     if(userProfile.isNullOrEmpty()){return Profile()
     }
    return GenericValues().getProfile(userProfile,context)
}


fun getKeys(keyWords: MutableList<Int>?, context: Context): String? {

    var keyTag = "";

    val keysCoach: ArrayList<CoachItem> = readAutoFillItems(context)

    val numbersIterator = keyWords!!.iterator()
    numbersIterator.let {
        while (numbersIterator.hasNext()) {
            var value = (numbersIterator.next())
            keyTag += " " + keysCoach.get(value).categoryname

        }
    }
    return keyTag;

}
fun String.onDatePickerClick( ) : Long {

    val formatter = SimpleDateFormat("dd/mm/yyyy")
    val date = formatter.parse(this) as Date
    return date.time
}

private fun readAutoFillItems(context: Context): ArrayList<CoachItem> {
    val c = GenericValues()
    return c.readCoachItems(context)
}

fun getDiscussionKeys(keyWords: MutableList<Int>?, context: Context): String? {

    var keyTag = "";

    keyWords.notNull {

        val keysCoach: ArrayList<CoachItem> = readDisscussions(context)

        val numbersIterator = keyWords!!.iterator()
        numbersIterator.let {
            while (numbersIterator.hasNext()) {
                var value = (numbersIterator.next())
                keyTag += " " + keysCoach.get(value).categoryname

            }
        }
    }
    return keyTag;

}


private fun readDisscussions(context: Context): ArrayList<CoachItem> {
    val c = GenericValues()
    return c.readDisuccsionTopics(context)
}

//var searchTags = listOf("university","college","exam","j2ee","neet", "gate",
//        " cat","jee", "clat" ,"bitsat", "srnjee", "viteee", "iit",
//        "engineering", "law", "medicine", "arts", "commerce", "science", "pharmacy", "journalism", "music", "mba",
//        "iim", "iisc", "arts", "computer", "cmat", "xat", "mat", "nift", "film", "fashion",
//        "jnu", "nimcet", "gmat", "toefl", "ielts", "gre", "gpat")


 fun String.sentenceToWords(): List<String> {
    val list1 = ArrayList<String>()
    val p: Pattern = Pattern.compile("[a-zA-Z]+")

    val m1: Matcher = p.matcher(this.toLowerCase(Locale.getDefault()))

    while (m1.find()) {

        list1.add(m1.group());
    }
    return list1.toSet().toList();
}

var firestoreSettings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
        .build()


fun getNotificationContentView(context: Context, title: String, message: String): View {
    val view = View.inflate(context, R.layout.notification_view, null)
    (view.findViewById<View>(R.id.not_title) as TextView).text = "$title"
    view.findViewById<View>(R.id.not_title).visibility = View.VISIBLE
    (view.findViewById<View>(R.id.not_message) as TextView).text = "$message"
    view.findViewById<View>(R.id.not_message).visibility = View.VISIBLE
    view.findViewById<View>(R.id.closeBtn).visibility = View.VISIBLE
    // view.findViewById<View>(R.id.closeBtn).setOnClickListener { v: View? -> EventBus.getDefault().post(NotificationBarHandler()) }
    return view
}


