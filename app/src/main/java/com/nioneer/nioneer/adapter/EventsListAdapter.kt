package com.nioneer.nioneer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ListItemAdsearcheventBinding
import com.nioneer.nioneer.listeners.AdSearchEventListener
import com.nioneer.nioneer.util.getAddress
import com.nioneer.nioneer.util.getKeys
import com.nioneer.nioneer.viewmodel.AdSearchModel

/**
 * Country recycler view adapter to view list of items
 */
class EventsListAdapter(private val adSearchModel: AdSearchModel) :
        AdSearchEventListener, RecyclerView.Adapter<EventsListAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_adsearchevent,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.eventsList[position]
            address = getAddress(countriesInfoModel!!.address)
            keyWordsTag = getKeys(countriesInfoModel!!.keyWords,viewHolder.itemView.context)
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
    }



    override fun getItemCount()= adSearchModel.eventsList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemAdsearcheventBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: AdSearchModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.eventsList[position].address!!.city)
        countriesViewModel.openFragment(countriesViewModel.eventsList[position],position)

    }
}
