package com.guiado.akbhar.communication


import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.BlogListRecyclerViewAdapter
import com.guiado.akbhar.utils.EndlessRecyclerViewScrollListener
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*


/**
 *  Class to handle recyclerview scroll listener and to initiate server call
 */
class RecyclerLoadClubsHandler(internal val albumsViewModel: HomeViewModel2, internal val listAdapter: BlogListRecyclerViewAdapter) {

    private val TAG = "RecyclerHandler"

    fun initRequest(view: RecyclerView) {
        Log.d(TAG, "initialise server request : sub url ")

        var urlSubString = "https://www.islamicfinder.org/"

        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {
                val retrofitClientInstance = RestHandler()
                val service = retrofitClientInstance.getServiceBlogArticles(urlSubString, view.context)
                val repositories = withContext(Dispatchers.Default) {
                    service.retrieveBlogArticles("world/morocco/").await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(view, repositories, urlSubString) }
            }
        }
    }

    private fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->

        Log.d("TAG", "coroutineHandler:exception ${exception}")

    }

    private fun coroutineSuccessHandler(view: RecyclerView, response: String, urlSubString: String) {
        Log.d("TAG", "coroutineHandler:success$response")

        var news: ArrayList<ClubsModel2> = ArrayList()

        //      if (urlSubString.equals("https://www.vulture.com/")) {
        news.addAll(variety(response))

        albumsViewModel.blogArticlesFilteredListModel.addAll(news)
        Log.d("TAG", "coroutineHandler:success${news.size}")

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                listAdapter.notifyDataSetChanged()
            }
        }

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




