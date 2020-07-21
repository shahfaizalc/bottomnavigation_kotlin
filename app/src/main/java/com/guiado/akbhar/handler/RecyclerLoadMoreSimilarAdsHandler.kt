package com.guiado.akbhar.handler

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.SimilarAdsAdapter
import com.guiado.akbhar.utils.EndlessRecyclerViewScrollListener
import com.guiado.akbhar.viewmodel.SimilarDiscussionModel


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreSimilarAdsHandler(private val countriesViewModel: SimilarDiscussionModel,
                                        private val listViewAdapter: SimilarAdsAdapter) {

    private val TAG = "CountryHandler"

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            }
        }
        recyclerView.addOnScrollListener(listener)
    }


    fun notifyAdapter(view: RecyclerView, curSize: Int) {
        view.post { listViewAdapter.notifyDataSetChanged() }
    }

    fun clearRecycleView(recyclerView: RecyclerView) {
        recyclerView.post { listViewAdapter.notifyItemRangeInserted(0, 0) }
    }

    fun resetRecycleView(recyclerView: RecyclerView) {
        countriesViewModel.talentProfilesList = countriesViewModel.talentProfilesList
        notifyAdapter(recyclerView, countriesViewModel.talentProfilesList.size)
    }


}
