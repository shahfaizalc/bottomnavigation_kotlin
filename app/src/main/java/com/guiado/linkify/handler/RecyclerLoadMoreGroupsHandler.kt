package com.guiado.linkify.handler

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.linkify.adapter.GroupsAdapter
import com.guiado.linkify.utils.EndlessRecyclerViewScrollListener
import com.guiado.linkify.viewmodel.GroupsModel


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreGroupsHandler(private val countriesViewModel: GroupsModel,
                                    private val listViewAdapter: GroupsAdapter) {

    private val TAG = "CountryHandler"

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                countriesViewModel.doGetTalents()
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