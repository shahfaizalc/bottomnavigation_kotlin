package com.guiado.akbhar.communication


import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import com.google.gson.Gson
import com.guiado.akbhar.model.Corona
import com.guiado.akbhar.model.Covid19
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*


/**
 *  Class to handle recyclerview scroll listener and to initiate server call
 */
class RecyclerLoadCovidHandler() {

    private val TAG = "RecyclerHandler"

    fun initRequest(view: Context) {
        Log.d(TAG, "initialise server request : sub url ")

        var urlSubString = "https://api.covid19api.com/"

        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {
                val retrofitClientInstance = RestHandler()
                val service = retrofitClientInstance.getServiceBlogCovid(urlSubString, view)
                val repositories = withContext(Dispatchers.Default) {
                    service.retrieveBlogArticles("summary").await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories,view) }
            }
        }
    }

    private fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->

        Log.d("TAG", "coroutineHandler:exception ${exception}")

    }

    private fun coroutineSuccessHandler(response: Covid19, view: Context) {
        Log.d("TAG", "coroutineHandler:success covid${response}")

        var talentListCorona= ObservableArrayList<Corona>()

        val corona2 = Corona()
        corona2.confirmed = "" + response.global.totalConfirmed
        corona2.death = "" + response.global.totalDeaths
        corona2.recovered = "" + response.global.totalRecovered
        talentListCorona.add(corona2)

        for (country in response.countries) {
            if (country.countryCode.equals("MA", true)) {
                val corona = Corona()
                corona.confirmed = "" + country.totalConfirmed
                corona.death = "" + country.totalDeaths
                corona.recovered = "" + country.totalRecovered
                talentListCorona.add(corona)

                val sharedPreference =  view.getSharedPreferences("COVID_INFO",Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("COVID_INFO",Gson().toJson(talentListCorona))
                editor.commit()
                return
            }
        }


//        var news: ArrayList<ClubsModel2> = ArrayList()
//
//        //      if (urlSubString.equals("https://www.vulture.com/")) {
//        news.addAll(variety(response))
//
//        albumsViewModel.blogArticlesFilteredListModel.addAll(news)
//        Log.d("TAG", "coroutineHandler:success${news.size}")
//
//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                listAdapter.notifyDataSetChanged()
//            }
//        }

    }

    fun variety(response: String): ArrayList<ClubsModel2> {
        var news: ArrayList<ClubsModel2> = ArrayList()
        val doc: Document = Jsoup.parse(response)

        //get images inside of the div
        val mainArticleDiv: Elements = doc.select("tr")

        val size: Int = mainArticleDiv.size

        Log.i("prayy", size.toString())

        for (index in 0..18) {

            val CITY = mainArticleDiv[index].select("td")[0].text()
            val FAJR = mainArticleDiv[index].select("td")[1].text()
            val SUNRISE = mainArticleDiv[index].select("td")[2].text()
            val DHUHR = mainArticleDiv[index].select("td")[3].text()
            val ASR = mainArticleDiv[index].select("td")[4].text()
            val MAGHRIB = mainArticleDiv[index].select("td")[5].text()
            val ISHA = mainArticleDiv[index].select("td")[6].text()
            val QIYAM = mainArticleDiv[index].select("td")[7].text()
            news.add(ClubsModel2(CITY, FAJR, SUNRISE, DHUHR, ASR, MAGHRIB, ISHA, QIYAM))

        }
        return news;
    }

}




