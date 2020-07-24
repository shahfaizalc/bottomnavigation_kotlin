package com.guiado.akbhar.util

import android.os.Handler
import androidx.viewpager.widget.ViewPager
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.guiado.akbhar.R
import com.guiado.akbhar.model.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd MMM yyyy")
    return format.format(date)
}

fun <T : Any> T?.notNull(function: (it: T) -> Unit) {
    if (this != null) function(this)
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

fun ViewPager.autoScroll(interval: Long) {

    val handler = Handler()
    var scrollPosition = 0

    val runnable = object : Runnable {

        override fun run() {
            val count = adapter?.count ?: 0
            if(count>0)
                setCurrentItem(scrollPosition++ % count, true)

            handler.postDelayed(this, interval)
        }
    }

    handler.post(runnable)
}


fun getNewsProviders(): ArrayList<NewsProviders> {
    var newsProviders = ArrayList<NewsProviders>();
    newsProviders.add(NewsProviders("hespress", R.drawable.np_hespress, "https://m.hespress.com/"))
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
    newsProviders.add(NewsProviders("foutni",R.drawable.np_foutni,  "http://foutni.com/"))
    newsProviders.add(NewsProviders("h24info", R.drawable.np_h24info_fr, "https://www.h24info.ma/"))
    //  newsProviders.add(NewsProviders("SaharaNow.com", "", "http://www.saharanow.com/"))
    newsProviders.add(NewsProviders("Chaab Press", R.drawable.np_chaabpress, "https://www.chaabpress.com/"))
    newsProviders.add(NewsProviders("Assabah", R.drawable.np_assabah, "https://assabah.ma/"))
    newsProviders.add(NewsProviders("La Nouvelle T", R.drawable.np_lanouvelletribune, "https://lnt.ma/"))
    return newsProviders;
}

fun getMagazines(): ArrayList<Magazines> {
    var newsProviders = ArrayList<Magazines>();
    newsProviders.add(Magazines("emarrakech", R.string.general, R.drawable.np_alyaoum24, "http://www.emarrakech.info/"))
    newsProviders.add(Magazines("fashion magazine", R.string.fashion, R.drawable.np_alyaoum24, "https://fashionmagazine.com/tag/morocco/"))
    newsProviders.add(Magazines("herdes magazine", R.string.artandculture, R.drawable.np_alyaoum24, "https://www.herdesmagazine.com/the-moroccan-issue/"))
    newsProviders.add(Magazines("kalimatmagazine", R.string.artandculture, R.drawable.np_hespress, "https://kalimatmagazine.com/Online-Articles-1"))
    newsProviders.add(Magazines("lalla fatema", R.string.general, R.drawable.np_alyaoum24, "https://www.lallafatema.ma/"))
    newsProviders.add(Magazines("maroc hebdo", R.string.tab_politics, R.drawable.np_yabiladi, "https://www.maroc-hebdo.press.ma/"))
    newsProviders.add(Magazines("moroccan ladies", R.string.women, R.drawable.np_le360, "http://moroccanladies.com/"))
    newsProviders.add(Magazines("north africa", R.string.tab_politics, R.drawable.np_hibapress, "http://north-africa.com/"))
    newsProviders.add(Magazines("sayidaty",R.string.women, R.drawable.np_alyaoum24, "https://www.sayidaty.net/"))
    newsProviders.add(Magazines("telquel", R.string.general, R.drawable.np_alyaoum24, "https://telquel.ma/"))
    newsProviders.add(Magazines("vogue(morocco)", R.string.fashion, R.drawable.np_alyaoum24, "https://en.vogue.me/tags/morocco/"))
    newsProviders.add(Magazines("zamane", R.string.tab_politics, R.drawable.np_febrayer, "https://zamane.ma/fr/"))
    return newsProviders;
}

