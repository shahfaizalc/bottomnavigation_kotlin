package com.guiado.grads.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.R
import com.guiado.grads.databinding.ListItemDiscussionBinding
import com.guiado.grads.listeners.DiscussionEventListener
import com.guiado.grads.util.convertLongToTime
import com.guiado.grads.util.getDiscussionCategories
import com.guiado.grads.viewmodel.DiscussionModel
import kotlin.random.Random

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
            keyWordsTag = ""+countriesInfoModel!!.statusC
          //  postDate= viewModel.talentProfilesList[position].postedDate?.toLong()?.let { convertLongToTime(it) }
            rating = Random.nextInt(0, 5).toString()
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
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].name)
      //  countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}
