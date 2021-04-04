package com.reelme.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.viewmodel.SalesViewModel
import com.reelme.app.databinding.SalesItemBinding
import com.reelme.app.model.Sale
import com.reelme.app.util.Constants

/**
 * Adapter to show the list of Sales
 */
class SaleItemsAdapter(private val salesViewModel: SalesViewModel) : RecyclerView.Adapter<SaleItemsAdapter.ViewHolder>() {

    /**
     * TAG
     */
    private val TAG = "SaleItemsAdapter"

    /**
     * List of Sales
     */
    private var sales: List<Sale>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SalesItemBinding.inflate(inflater, parent, false)
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

    inner class ViewHolder( val binding: SalesItemBinding) : RecyclerView.ViewHolder(binding.root)
}