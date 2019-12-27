package com.guiado.grads.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.R
import com.guiado.grads.databinding.ListItemAdsearchBinding
import com.guiado.grads.databinding.ListItemDiscussionBinding
import com.guiado.grads.databinding.ListItemMyadsBinding
import com.guiado.grads.databinding.ListItemMydiscussionBinding
import com.guiado.grads.listeners.MyDiscussionEventListener
import com.guiado.grads.util.convertLongToTime
import com.guiado.grads.util.getDiscussionKeys
import com.guiado.grads.viewmodel.MyDiscussionModel

/**
 * Country recycler view adapter to view list of items
 */
class MyDiscussionAdapter(private val adSearchModel: MyDiscussionModel) :
        MyDiscussionEventListener, RecyclerView.Adapter<MyDiscussionAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_mydiscussion,
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
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
    }



    override fun getItemCount()= adSearchModel.talentProfilesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemMydiscussionBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: MyDiscussionModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}
