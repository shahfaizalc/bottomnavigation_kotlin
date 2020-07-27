package com.guiado.akbhar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ListItemDiscussionBinding
import com.guiado.akbhar.databinding.ListItemEntertainmentBinding
import com.guiado.akbhar.listeners.DiscussionEventListener
import com.guiado.akbhar.listeners.EntertainmentEventListener
import com.guiado.akbhar.viewmodel.DiscussionModel
import com.guiado.akbhar.viewmodel.EntertainementViewModel
import com.guiado.akbhar.viewmodel.FoodViewModel

/**
 * Country recycler view adapter to view list of items
 */
class EntertainmentAdapter(private val adSearchModel: EntertainementViewModel) :
        EntertainmentEventListener, RecyclerView.Adapter<EntertainmentAdapter.ViewHolder>(){

    private val TAG = "EntertainmentAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_entertainment,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.talentProfilesList[position]
          //  keyWordsTag = getDiscussionCategories(countriesInfoModel!!.keyWords,viewHolder.itemView.context)
           // postDate= viewModel.talentProfilesList[position].postedDate?.toLong()?.let { convertLongToTime(it) }
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
        var binding: ListItemEntertainmentBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: EntertainementViewModel, position: Int) {
     //   Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }

    override fun launchNews(countriesViewModel: EntertainementViewModel, position: Int) {
        countriesViewModel.openFragment3(countriesViewModel.talentProfilesList[position],position)
    }

    override fun launchShare(countriesViewModel: EntertainementViewModel, position: Int) {
        countriesViewModel.openShare(countriesViewModel.talentProfilesList[position],position)
    }
}
