package com.reelme.app.handler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.adapter.DailyBonusReelsAdapter
import com.reelme.app.adapter.FollowAdapter
import com.reelme.app.adapter.GoalAdapter
import com.reelme.app.adapter.ReferAdapter
import com.reelme.app.utils.EndlessRecyclerViewScrollListener
import com.reelme.app.viewmodel.DailyBonusReelsModel
import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.GoalModel
import com.reelme.app.viewmodel.ReferModel


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreDailyHandler(private val countriesViewModel: DailyBonusReelsModel,
                                   private val listViewAdapter: DailyBonusReelsAdapter) {

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
