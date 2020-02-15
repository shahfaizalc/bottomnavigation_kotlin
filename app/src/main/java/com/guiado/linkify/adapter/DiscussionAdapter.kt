package com.guiado.linkify.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ListItemDiscussionBinding
import com.guiado.linkify.listeners.DiscussionEventListener
import com.guiado.linkify.util.convertLongToTime
import com.guiado.linkify.util.getDiscussionCategories
import com.guiado.linkify.viewmodel.DiscussionModel

/**
 * Country recycler view adapter to view list of items
 */
class DiscussionAdapter(private val adSearchModel: DiscussionModel) :
        DiscussionEventListener, RecyclerView.Adapter<DiscussionAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_discussion,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.talentProfilesList[position]
          //  keyWordsTag = getDiscussionCategories(countriesInfoModel!!.keyWords,viewHolder.itemView.context)
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
        var binding: ListItemDiscussionBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: DiscussionModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
      //  countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}
