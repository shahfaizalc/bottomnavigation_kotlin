package com.guiado.akbhar.handler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.DiscussionAdapter
import com.guiado.akbhar.adapter.GameAdapter
import com.guiado.akbhar.utils.EndlessRecyclerViewScrollListener
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.GameViewModel


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreGameHandler(private val countriesViewModel: GameViewModel,
                                  private val listViewAdapter: GameAdapter) {

    private val TAG = "Discussion"

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

                Log.d(TAG, "initRequest: sub scrollListener ")
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
