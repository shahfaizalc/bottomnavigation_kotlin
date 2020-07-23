package com.guiado.akbhar.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ListNewsprovidersItemBinding
import com.guiado.akbhar.listeners.IntroChannelEventListener
import com.guiado.akbhar.model.NewsProviders
import com.guiado.akbhar.viewmodel.NewsProvidersViewModel


class NewsProvidersRecyclerViewAdapter(private val channelsViewModel: NewsProvidersViewModel, private val channelTamilMovieReviewDataModel: ArrayList<NewsProviders>) :
        RecyclerView.Adapter<NewsProvidersRecyclerViewAdapter.ViewHolder>(), IntroChannelEventListener {
    private val TAG = "NewsProvidersRecyclerAd"

    override fun onClickYearListItem(channelsViewModel: NewsProvidersViewModel, position: Int) {

        Log.d(TAG,"idchan "+channelTamilMovieReviewDataModel.get(position));

        channelsViewModel.openFragment(channelTamilMovieReviewDataModel.get(position).homeurl!!)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_newsproviders_item,
            viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = channelsViewModel
        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!){
            titleTextView = channelTamilMovieReviewDataModel[position].title
            val l = channelTamilMovieReviewDataModel[position].imgurl
            imges.setImageDrawable(ContextCompat.getDrawable(viewHolder.contextt!!, channelTamilMovieReviewDataModel[position].imgurl!!))
          //  imgUrl = if (l != null) l else ""
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)

    }

    override fun getItemCount(): Int {
        return channelTamilMovieReviewDataModel.size
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListNewsprovidersItemBinding? = null
        var contextt : Context? = null

        init {
            binding = DataBindingUtil.bind(itemView)
            contextt = itemView.context

        }
    }
}