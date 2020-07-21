package com.guiado.akbhar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ListItemCoronaBinding
import com.guiado.akbhar.databinding.ListItemDiscussionBinding
import com.guiado.akbhar.listeners.CoronaEventListener
import com.guiado.akbhar.listeners.DiscussionEventListener
import com.guiado.akbhar.viewmodel.CoronaModel
import com.guiado.akbhar.viewmodel.DiscussionModel

/**
 * Country recycler view adapter to view list of items
 */
class CoronaAdapter(private val adSearchModel: CoronaModel) :
        CoronaEventListener, RecyclerView.Adapter<CoronaAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_corona,
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
        var binding: ListItemCoronaBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: CoronaModel, position: Int) {
     //   Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }

    override fun launchNews(countriesViewModel: CoronaModel, position: Int) {
        countriesViewModel.openFragment3(countriesViewModel.talentProfilesList[position],position)
    }
}
