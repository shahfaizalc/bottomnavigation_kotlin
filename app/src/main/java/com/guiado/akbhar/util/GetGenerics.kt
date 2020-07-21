package com.guiado.akbhar.util

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.guiado.akbhar.R
import com.guiado.akbhar.model.Address
import com.guiado.akbhar.model.CoachItem
import com.guiado.akbhar.model.NewsProviders
import com.guiado.akbhar.model2.Profile
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


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

fun storeUserName(context: Context, id: String, name: Profile) {
    val sharedPreference = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putString(id, GenericValues().profileToString(name))
    editor.apply()
}

fun getUserName(context: Context, id: String): Profile {
    val sharedPreference = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
    var userProfile = sharedPreference.getString(id, "")
    if (userProfile.isNullOrEmpty()) {
        return Profile()
    }
    return GenericValues().getProfile(userProfile, context)
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

fun String.onDatePickerClick(): Long {

    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val date = formatter.parse(this) as Date
    return date.time
}

private fun readAutoFillItems(context: Context): ArrayList<CoachItem> {
    val c = GenericValues()
    return c.readCoachItems(context)
}

fun getDiscussionCategories(keyWords: MutableList<Int>?, context: Context): String? {

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

fun getNewsProviders(): ArrayList<NewsProviders> {

    var newsProviders = ArrayList<NewsProviders>();
    newsProviders.add(NewsProviders("هسبريس", R.drawable.np_hespress, ""))
    newsProviders.add(NewsProviders("Hiba Press", R.drawable.np_hibapress, ""))
    newsProviders.add(NewsProviders("le360.ma", R.drawable.np_le360, ""))
    newsProviders.add(NewsProviders("Goud", R.drawable.np_goud, ""))
    newsProviders.add(NewsProviders("Febrayer", R.drawable.np_febrayer, ""))
    newsProviders.add(NewsProviders("yabiladi.com", R.drawable.np_yabiladi, ""))
    newsProviders.add(NewsProviders("Alyaoum24", R.drawable.np_alyaoum24, ""))
    newsProviders.add(NewsProviders("Akhbarona.com", R.drawable.np_akhbarona, ""))
    newsProviders.add(NewsProviders("Kifache", R.drawable.np_kifache, ""))
    newsProviders.add(NewsProviders("almaghribtoda", R.drawable.np_almaghribtoday, ""))
    newsProviders.add(NewsProviders("Souss24.com", R.drawable.np_souss24, ""))
    newsProviders.add(NewsProviders("Tanja24.com", R.drawable.np_tanja24, ""))
    newsProviders.add(NewsProviders("Morocco World", R.drawable.np_moroccoworldnews, ""))
    newsProviders.add(NewsProviders("Agora", R.drawable.np_agora, ""))
    newsProviders.add(NewsProviders("n24.ma", R.drawable.np_n24, ""))
    newsProviders.add(NewsProviders("Maghress.com", R.drawable.np_maghress, ""))
    newsProviders.add(NewsProviders("Menara", R.drawable.np_menara, ""))
    newsProviders.add(NewsProviders("Dalil-Rif.com", R.drawable.np_dalilrif, ""))
    newsProviders.add(NewsProviders("NadorCity.com", R.drawable.np_nadorcity, ""))
    newsProviders.add(NewsProviders("TelexPresse.c", R.drawable.np_telexpress, ""))
    newsProviders.add(NewsProviders("bladi.net", R.drawable.np_bladi, ""))
    newsProviders.add(NewsProviders("Rue20", R.drawable.np_rue20, ""))
    newsProviders.add(NewsProviders("PressTetouan.", R.drawable.np_presstetouan, ""))
    newsProviders.add(NewsProviders("Agadir24", R.drawable.np_agadir24, ""))
   // newsProviders.add(NewsProviders("Fraja-Maroc.n", "", ""))
    newsProviders.add(NewsProviders("h24info", R.drawable.np_h24info_fr, ""))
  //  newsProviders.add(NewsProviders("SaharaNow.com", "", ""))
    newsProviders.add(NewsProviders("Chaab Press", R.drawable.np_chaabpress, ""))
    newsProviders.add(NewsProviders("Assabah", R.drawable.np_assabah, ""))
    newsProviders.add(NewsProviders("La Nouvelle T", R.drawable.np_lanouvelletribune, ""))
 return newsProviders;

}


