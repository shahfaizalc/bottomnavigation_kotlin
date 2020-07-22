package com.guiado.akbhar.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ListMagazinesItemBinding
import com.guiado.akbhar.listeners.MagazineEventListener
import com.guiado.akbhar.model.Magazines
import com.guiado.akbhar.viewmodel.MagazineViewModel
import java.util.*
import kotlin.collections.ArrayList


class MagazineRecyclerViewAdapter(private val channelsViewModel: MagazineViewModel, private val channelTamilMovieReviewDataModel: ArrayList<Magazines>) :
        RecyclerView.Adapter<MagazineRecyclerViewAdapter.ViewHolder>(), MagazineEventListener {
    override fun onClickYearListItem(channelsViewModel: MagazineViewModel, position: Int) {

        Log.d("idChan","idchan "+channelTamilMovieReviewDataModel.get(position));

        channelsViewModel.openFragment(channelTamilMovieReviewDataModel.get(position)!!.homeurl!!)

    }

    private val TAG = "ArtistRecyclerAdapter"


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_magazines_item,
            viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = channelsViewModel
        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!){
            titleTextView = channelTamilMovieReviewDataModel[position].title!!.toUpperCase(Locale.ROOT)
            titleCategoryTextView = channelTamilMovieReviewDataModel[position].category.name.toLowerCase(Locale.ROOT)

            val l = channelTamilMovieReviewDataModel[position].imgurl
            //imges.setImageDrawable(ContextCompat.getDrawable(viewHolder.contextt!!, channelTamilMovieReviewDataModel[position].imgurl!!))
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
        var binding: ListMagazinesItemBinding? = null
        var contextt : Context? = null

        init {
            binding = DataBindingUtil.bind(itemView)
            contextt = itemView.context

        }
    }
}