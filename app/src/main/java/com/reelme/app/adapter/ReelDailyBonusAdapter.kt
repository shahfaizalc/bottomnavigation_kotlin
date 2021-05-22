package com.reelme.app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.R
import com.reelme.app.databinding.ListItemDailyBinding
import com.reelme.app.databinding.ListItemFollowBinding
import com.reelme.app.databinding.ListItemReeldailyBinding
import com.reelme.app.databinding.ListItemReferBinding
import com.reelme.app.listeners.DailyBonusReelsEventListener
import com.reelme.app.listeners.FollowEventListener
import com.reelme.app.listeners.ReelDailyBonusReelsEventListener
import com.reelme.app.listeners.ReferEventListener
import com.reelme.app.viewmodel.DailyBonusReelsModel
import com.reelme.app.viewmodel.FollowModel
import com.reelme.app.viewmodel.ReelDailyBonusMobileViewModel
import com.reelme.app.viewmodel.ReferModel


/**
 * Country recycler view adapter to view list of items
 */
class ReelDailyBonusAdapter(private val adSearchModel: ReelDailyBonusMobileViewModel) :
        ReelDailyBonusReelsEventListener, RecyclerView.Adapter<ReelDailyBonusAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_reeldaily,
            viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            Log.d("faizay", "faizay rach "+position+" "+viewModel.dailyBonusTopics.size
            +" "+(position*2)+" "+((position*2)+1))

            countriesInfoModel = viewModel.dailyBonusTopics[position*2]
            dailyReelItemVisible = View.VISIBLE
            try{
                countriesInfoModel2 = viewModel.dailyBonusTopics[((position*2)+1)]
            }catch (e: IndexOutOfBoundsException){
                countriesInfoModel2 = viewModel.dailyBonusTopics[position*2]
                dailyReelItemVisible = View.INVISIBLE
            }
            keyWordsTag = ""+countriesInfoModel!!.topicDescription
          //  postDate= viewModel.talentProfilesList[position].postedDate?.toLong()?.let { convertLongToTime(it) }
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.itemClickListener = this
    }



    override fun getItemCount()= adSearchModel.dailyBonusTopics.size - adSearchModel.dailyBonusTopics.size/2

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemReeldailyBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: ReelDailyBonusMobileViewModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.dailyBonusTopics[position].topicDescription)
      //  countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }
}
