package com.faizal.bottomnavigation.handler

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faizal.bottomnavigation.adapter.AdSearchRecyclerViewAdapter
import com.faizal.bottomnavigation.utils.EndlessRecyclerViewScrollListener
import com.faizal.bottomnavigation.viewmodel.AdSearchModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.runBlocking


/**
 *  Class to handle recyclerview scroll listner and to initiate server call
 */
class RecyclerLoadMoreCountryHandler(private val countriesViewModel: AdSearchModel,
                                     private val listViewAdapter: AdSearchRecyclerViewAdapter) {

    private val TAG = "CountryHandler"

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            }
        }
        recyclerView.addOnScrollListener(listener)
    }

    fun initRequest(view: RecyclerView) {
        Log.d(TAG, "initRequest: sub url ")
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("ads");
        query.whereEqualTo("address.city",countriesViewModel.city)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        runBlocking {
                            for (document in task.result!!) {
                                Log.d(TAG, "Sucess getting documents: " + document.id + " => " + document.data)
                                countriesViewModel.addListItem(document)
                            }
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                    }
                }).addOnFailureListener(OnFailureListener { exception -> Log.d(TAG, "Error getting documents: ", exception) })
                .addOnSuccessListener(OnSuccessListener { valu -> Log.d(TAG, "Error getting documents: "+valu.size()) })
    }


    fun notifyAdapter(view: RecyclerView, curSize: Int) {
        view.post { listViewAdapter.notifyDataSetChanged() }
    }

    fun clearRecycleView(recyclerView: RecyclerView) {
        recyclerView.post { listViewAdapter.notifyItemRangeInserted(0, 0) }
    }

    fun resetRecycleView(recyclerView: RecyclerView) {
        countriesViewModel.countriesInfoModel = countriesViewModel.countriesInfoModel
        notifyAdapter(recyclerView, countriesViewModel.countriesInfoModel.size)
    }


}
