package com.faizal.bottomnavigation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.*
import com.faizal.bottomnavigation.listeners.*
import com.faizal.bottomnavigation.util.convertLongToTime
import com.faizal.bottomnavigation.util.getAddress
import com.faizal.bottomnavigation.util.getDiscussionKeys
import com.faizal.bottomnavigation.util.getKeys
import com.faizal.bottomnavigation.viewmodel.*

/**
 * Country recycler view adapter to view list of items
 */
class TheEventsAdapter(private val adSearchModel: TheEventsModel) :
        TheEventsEventListener, RecyclerView.Adapter<TheEventsAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_theevents,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.talentProfilesList[position]
            keyWordsTag = getDiscussionKeys(countriesInfoModel!!.keyWords,viewHolder.itemView.context)
            postDate= viewModel.talentProfilesList[position].postedDate?.toLong()?.let { convertLongToTime(it) }
            itemPosition = position
            mainDataModel = viewModel
            location = viewModel.talentProfilesList[position]!!.address?.locationname +" "+viewModel.talentProfilesList[position]!!.address?.streetName +" "+viewModel.talentProfilesList[position]!!.address?.town +" "+viewModel.talentProfilesList[position]!!.address?.city


            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
    }



    override fun getItemCount()= adSearchModel.talentProfilesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemTheeventsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: TheEventsModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}
