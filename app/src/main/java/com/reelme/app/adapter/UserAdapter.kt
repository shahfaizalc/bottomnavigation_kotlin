package com.reelme.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.databinding.SalesItemBinding
import com.reelme.app.databinding.UserRecyclerLisitemBinding
import com.reelme.app.model.Sale
import com.reelme.app.util.Constants
import com.reelme.app.viewmodel.UserViewModel

class UserAdapter(private val salesViewModel: UserViewModel) :  RecyclerView.Adapter<UserAdapter.ViewHolder>(), BindableAdapter<Sale> {

    private var sales: List<Sale>? = null

    override fun setData(items: List<Sale>) {
        sales = items
        notifyDataSetChanged()
    }

    override fun changedPositions(positions: Set<Int>) {
        positions.forEach(this::notifyItemChanged)
    }

    /**
     * TAG
     */
    private val TAG = "SaleItemsAdapter"

    /**
     * List of Sales
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserRecyclerLisitemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val viewModel = salesViewModel
        viewHolder.binding.apply {
            postData = sales!![i]
            mainData = viewModel
            if (!sales!![i].status!!.equals(Constants.SOLDOUT, ignoreCase = true))
                saleState = View.GONE
        }
        viewHolder.binding.simpleListAdapter = this
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount()= if (sales == null) 0 else sales!!.size

    override fun getItemId(position: Int)= position.toLong()

    override fun getItemViewType(position: Int)= position

    fun setSalesItems(salesItems: List<Sale>) {
        Log.d(TAG, "setSalesItems:")
        this.sales = salesItems

        notifyDataSetChanged()
    }

    inner class ViewHolder( val binding: UserRecyclerLisitemBinding) : RecyclerView.ViewHolder(binding.root)




}
