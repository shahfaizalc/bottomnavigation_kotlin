package com.guiado.grads.handler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.adapter.ChallengeAdapter
import com.guiado.grads.adapter.DiscussionAdapter
import com.guiado.grads.adapter.GoalAdapter
import com.guiado.grads.utils.EndlessRecyclerViewScrollListener
import com.guiado.grads.viewmodel.ChallengeModel
import com.guiado.grads.viewmodel.DiscussionModel
import com.guiado.grads.viewmodel.GoalModel


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreGoalHandler(private val countriesViewModel: GoalModel,
                                  private val listViewAdapter: GoalAdapter) {

    private val TAG = "Discussion"

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

                Log.d(TAG, "initRequest: sub scrollListener ")
               // countriesViewModel.doGetTalents()
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