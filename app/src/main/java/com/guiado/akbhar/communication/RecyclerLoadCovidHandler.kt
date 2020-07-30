package com.guiado.akbhar.communication


import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import com.google.gson.Gson
import com.guiado.akbhar.model.Corona
import com.guiado.akbhar.model.Covid19
import kotlinx.coroutines.*
import java.util.*


/**
 *  Class to handle recyclerview scroll listener and to initiate server call
 */
class RecyclerLoadCovidHandler() {

    private val TAG = "RecyclerHandler"

    fun initRequest(view: Context) {

        val sharedPreference = view.getSharedPreferences("COVID_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getLong("COVID_INFO_TIME", 0);

        if (coronaJson == 0L) {
            Log.d(TAG, "initialise server request : sub url ")
            requestStart(view)
        } else if (System.currentTimeMillis() > (coronaJson + (1000 * 60 * 60 * 12))) {
            requestStart(view)
        }

    }

    fun requestStart(view: Context){
        Log.d(TAG, "initialise server request : sub url ")

        val urlSubString = "https://api.covid19api.com/"

        runBlocking {
            val handler = coroutineExceptionHandler()
            GlobalScope.launch(handler) {
                val retrofitClientInstance = RestHandler()
                val service = retrofitClientInstance.getServiceBlogCovid(urlSubString, view)
                val repositories = withContext(Dispatchers.Default) {
                    service.retrieveBlogArticles("summary").await()
                }
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories, view) }
            }
        }
    }

    private fun coroutineExceptionHandler() = CoroutineExceptionHandler { _, exception ->

        Log.d("TAG", "coroutineHandler:exception ${exception}")

    }

    private fun coroutineSuccessHandler(response: Covid19, view: Context) {
        Log.d("TAG", "coroutineHandler:success covid ${response}")

        var talentListCorona = ObservableArrayList<Corona>()

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

                val sharedPreference = view.getSharedPreferences("COVID_INFO", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("COVID_INFO", Gson().toJson(talentListCorona))
                editor.putLong("COVID_INFO_TIME", System.currentTimeMillis());
                editor.apply()
                return
            }
        }

    }
}




