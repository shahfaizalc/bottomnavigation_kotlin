package com.nioneer.nioneer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ListItemAdsearchBinding
import com.nioneer.nioneer.databinding.ListItemMyadsBinding
import com.nioneer.nioneer.databinding.ListItemSimilaradsBinding
import com.nioneer.nioneer.listeners.SimilarAdsEventListener
import com.nioneer.nioneer.util.getAddress
import com.nioneer.nioneer.util.getKeys
import com.nioneer.nioneer.viewmodel.SimilarDiscussionModel

/**
 * Country recycler view adapter to view list of items
 */
class SimilarAdsAdapter(private val adSearchModel: SimilarDiscussionModel) :
        SimilarAdsEventListener, RecyclerView.Adapter<SimilarAdsAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_similarads,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.talentProfilesList[position]
            address = getAddress(countriesInfoModel!!.address)
            keyWordsTag = getKeys(countriesInfoModel!!.keyWords,viewHolder.itemView.context)
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
    }



    override fun getItemCount()= adSearchModel.talentProfilesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemSimilaradsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: SimilarDiscussionModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].address!!.city)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}