package com.guiado.akbhar.util

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.guiado.akbhar.R
import com.guiado.akbhar.model.*
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
    newsProviders.add(NewsProviders("هسبريس", R.drawable.np_hespress, "https://m.hespress.com/"))
    newsProviders.add(NewsProviders("Hiba Press", R.drawable.np_hibapress, "https://ar.hibapress.com/"))
    newsProviders.add(NewsProviders("le360.ma", R.drawable.np_le360, "https://m.le360.ma/"))
    newsProviders.add(NewsProviders("Febrayer", R.drawable.np_febrayer, "https://m.febrayer.com/"))
    newsProviders.add(NewsProviders("yabiladi.com", R.drawable.np_yabiladi, "https://www.yabiladi.com/"))
    newsProviders.add(NewsProviders("Alyaoum24", R.drawable.np_alyaoum24, "https://m.alyaoum24.com/"))
    newsProviders.add(NewsProviders("Dalil-Rif.com", R.drawable.np_dalilrif, "https://dalil-rif.com/mobile/"))
    newsProviders.add(NewsProviders("Kifache", R.drawable.np_kifache, "https://kifache.com/"))
    newsProviders.add(NewsProviders("almaghribtoda", R.drawable.np_almaghribtoday, "https://www.almaghribtoday.net/"))
    newsProviders.add(NewsProviders("Souss24.com", R.drawable.np_souss24, "https://www.souss24.com/"))
    newsProviders.add(NewsProviders("Tanja24.com", R.drawable.np_tanja24, "https://www.tanja24.com/"))
    newsProviders.add(NewsProviders("Morocco World", R.drawable.np_moroccoworldnews, "https://www.moroccoworldnews.com/"))
    newsProviders.add(NewsProviders("Agora", R.drawable.np_agora, "https://www.agora.ma/"))
    newsProviders.add(NewsProviders("n24.ma", R.drawable.np_n24, "https://www.n24.ma/"))
    newsProviders.add(NewsProviders("Maghress.com", R.drawable.np_maghress, "https://www.maghress.com/"))
    newsProviders.add(NewsProviders("Menara", R.drawable.np_menara, "https://www.menara.ma/"))
    newsProviders.add(NewsProviders("NadorCity.com", R.drawable.np_nadorcity, "https://m.nadorcity.com/"))
    newsProviders.add(NewsProviders("Goud", R.drawable.np_goud, "https://goud.ma/"))
    newsProviders.add(NewsProviders("Akhbarona.com", R.drawable.np_akhbarona, "https://www.akhbarona.com/"))
    newsProviders.add(NewsProviders("TelexPresse", R.drawable.np_telexpress, "https://www.telexpresse.com/"))
    newsProviders.add(NewsProviders("bladi.net", R.drawable.np_bladi, "https://www.bladi.net/"))
    newsProviders.add(NewsProviders("Rue20", R.drawable.np_rue20, "https://www.rue20.com/"))
    newsProviders.add(NewsProviders("PressTetouan.", R.drawable.np_presstetouan, "https://www.presstetouan.com/"))
    newsProviders.add(NewsProviders("Agadir24", R.drawable.np_agadir24, "https://agadir24.info/mobile/"))
    // newsProviders.add(NewsProviders("Fraja-Maroc.n", "", "http://www.fraja-maroc.net/"))
    newsProviders.add(NewsProviders("h24info", R.drawable.np_h24info_fr, "https://www.h24info.ma/"))
    //  newsProviders.add(NewsProviders("SaharaNow.com", "", "http://www.saharanow.com/"))
    newsProviders.add(NewsProviders("Chaab Press", R.drawable.np_chaabpress, "https://www.chaabpress.com/"))
    newsProviders.add(NewsProviders("Assabah", R.drawable.np_assabah, "https://assabah.ma/"))
    newsProviders.add(NewsProviders("La Nouvelle T", R.drawable.np_lanouvelletribune, "https://lnt.ma/"))
    return newsProviders;
}

fun getMagazines(): ArrayList<Magazines> {
    var newsProviders = ArrayList<Magazines>();
    newsProviders.add(Magazines("kalimatmagazine", MagazineCategory.ART_AND_CULTURE, R.drawable.np_hespress, "https://kalimatmagazine.com/Online-Articles-1"))
    newsProviders.add(Magazines("north africa", MagazineCategory.POLITICS, R.drawable.np_hibapress, "http://north-africa.com/"))
    newsProviders.add(Magazines("moroccan ladies", MagazineCategory.WOMEN, R.drawable.np_le360, "http://moroccanladies.com/"))
    newsProviders.add(Magazines("zamane", MagazineCategory.POLITICS, R.drawable.np_febrayer, "https://zamane.ma/fr/"))
    newsProviders.add(Magazines("maroc hebdo", MagazineCategory.POLITICS, R.drawable.np_yabiladi, "https://www.maroc-hebdo.press.ma/"))
    newsProviders.add(Magazines("emarrakech", MagazineCategory.GENERAL, R.drawable.np_alyaoum24, "http://www.emarrakech.info/"))
    newsProviders.add(Magazines("lalla fatema", MagazineCategory.GENERAL, R.drawable.np_alyaoum24, "https://www.lallafatema.ma/"))
    newsProviders.add(Magazines("sayidaty", MagazineCategory.WOMEN, R.drawable.np_alyaoum24, "https://www.sayidaty.net/"))
    newsProviders.add(Magazines("fashion magazine", MagazineCategory.FASHION, R.drawable.np_alyaoum24, "https://fashionmagazine.com/tag/morocco/"))
    newsProviders.add(Magazines("herdes magazine", MagazineCategory.ART_AND_CULTURE, R.drawable.np_alyaoum24, "https://www.herdesmagazine.com/the-moroccan-issue/"))
    newsProviders.add(Magazines("telquel", MagazineCategory.GENERAL, R.drawable.np_alyaoum24, "https://telquel.ma/"))
    newsProviders.add(Magazines("vogue(morocco)", MagazineCategory.FASHION, R.drawable.np_alyaoum24, "https://en.vogue.me/tags/morocco/"))
    return newsProviders;
}

