package com.guiado.akbhar.handler

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.IntroChannelsRecyclerViewAdapter
import com.guiado.akbhar.adapter.MagazineRecyclerViewAdapter
import com.guiado.akbhar.model.Magazines
import com.guiado.akbhar.model.NewsProviders
import com.guiado.akbhar.utils.EndlessRecyclerViewScrollListener
import com.guiado.akbhar.viewmodel.IntroViewModel
import com.guiado.akbhar.viewmodel.MagazineViewModel

class RecyclerLoadMoreMagazineHandler(private val channelsViewModel: MagazineViewModel,
                                      private val listViewAdapter: MagazineRecyclerViewAdapter,
                                      private val channelTamilComedyDataModel: ArrayList<Magazines>)    {

    private val TAG = "RecyclerLoadMoreChannelsHandler"


    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        recyclerView.addOnScrollListener(listener)
    }

    fun initRequest(view: RecyclerView, urlSubString: ArrayList<Magazines>, isNew: Boolean) {
        var urlList = urlSubString
        Log.d("TAG", "initRequest: next " + urlSubString)
        channelTamilComedyDataModel.addAll(urlSubString)
        view.post { listViewAdapter.notifyDataSetChanged()}
    }


}