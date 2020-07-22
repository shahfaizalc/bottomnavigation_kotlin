package com.guiado.akbhar.handler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.DiscussionAdapter
import com.guiado.akbhar.adapter.TechnologyAdapter
import com.guiado.akbhar.utils.EndlessRecyclerViewScrollListener
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.TechViewModel
import javax.xml.transform.sax.TemplatesHandler


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreTechnologyHandler(private val countriesViewModel: TechViewModel,
                                        private val listViewAdapter: TechnologyAdapter) {

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
