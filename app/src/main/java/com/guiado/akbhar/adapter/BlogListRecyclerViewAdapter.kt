package com.guiado.akbhar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.communication.HomeViewModel2
import com.guiado.akbhar.databinding.ListItemBlogBinding

/**
 * Blog recycler view adapter class is to view list of blog articles.
 */
class BlogListRecyclerViewAdapter(private val homeViewModel: HomeViewModel2) :
        RecyclerView.Adapter<BlogListRecyclerViewAdapter.ViewHolder>() {

    /**
     * TAG : class name
     */
    private val TAG = "BlogListRecyclerViewAdapter"



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) = ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_blog, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            blogItemModel = homeViewModel.blogArticlesFilteredListModel[position]
            itemPosition = position
            mainDataModel = homeViewModel
            executePendingBindings()
        }
    }

    override fun getItemCount() = homeViewModel.blogArticlesFilteredListModel.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemBlogBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}