package com.guiado.akbhar.handler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.NewsProvidersRecyclerViewAdapter
import com.guiado.akbhar.model.NewsProviders
import com.guiado.akbhar.utils.EndlessRecyclerViewScrollListener
import com.guiado.akbhar.viewmodel.NewsProvidersViewModel

class RecyclerLoadMoreNewsProvidersComedyHandler(private val channelsViewModel: NewsProvidersViewModel,
                                                 private val listViewAdapter: NewsProvidersRecyclerViewAdapter,
                                                 private val channelTamilComedyDataModel: ArrayList<NewsProviders>)    {

    private val TAG = "RecyclerLoadMoreChannelsHandler"


    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        recyclerView.addOnScrollListener(listener)
    }

    fun initRequest(view: RecyclerView, urlSubString: ArrayList<NewsProviders>, isNew: Boolean) {
        var urlList = urlSubString
        Log.d("TAG", "initRequest: next " + urlSubString)
        channelTamilComedyDataModel.addAll(urlSubString)
        view.post { listViewAdapter.notifyDataSetChanged()}
    }


}