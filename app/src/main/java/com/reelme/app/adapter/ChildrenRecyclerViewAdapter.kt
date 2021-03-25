package com.reelme.app.adapter

import com.reelme.app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.databinding.ChildrenItemLayoutBinding
import com.reelme.app.listeners.ChildrenEventListener
import com.reelme.app.model.Flight
import com.reelme.app.pojos.Child


class ChildrenRecyclerViewAdapter(flsLst: List<Child>, ctx: Context) : RecyclerView.Adapter<ChildrenRecyclerViewAdapter.ViewHolder>(), ChildrenEventListener {
    private val flightsList: List<Child>
    private val context: Context
    var selectedPosition=-1;
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding: ChildrenItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.children_item_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight: Child = flightsList[position]
        holder.flightItemBinding.flight = flight
        holder.flightItemBinding.itemClickListener = this
        holder.flightItemBinding.itemPosition = position

        holder.flightItemBinding.isValid = selectedPosition==position

    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

    inner class ViewHolder(flightItemLayoutBinding: ChildrenItemLayoutBinding) : RecyclerView.ViewHolder(flightItemLayoutBinding.root) {
        var flightItemBinding: ChildrenItemLayoutBinding = flightItemLayoutBinding

    }

    init {
        flightsList = flsLst
        context = ctx
    }



    override fun bookFlight(f: Child, view: View?, itemPosition: Int) {
//        Toast.makeText(context, "You booked " + f.children,
//                Toast.LENGTH_LONG).show()
        selectedPosition = itemPosition
        notifyDataSetChanged()
    }
    fun getSelectedItem() = selectedPosition

}