package com.guiado.akbhar.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ListItemAdsearchBinding
import com.guiado.akbhar.databinding.ListItemMyadsBinding
import com.guiado.akbhar.listeners.MyAdsEventListener
import com.guiado.akbhar.util.getAddress
import com.guiado.akbhar.util.getKeys
import com.guiado.akbhar.viewmodel.MyAdsModel

/**
 * Country recycler view adapter to view list of items
 */
class MyAdsAdapter(private val adSearchModel: MyAdsModel) :
        MyAdsEventListener, RecyclerView.Adapter<MyAdsAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_myads,
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
        var binding: ListItemMyadsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: MyAdsModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].address!!.city)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}
