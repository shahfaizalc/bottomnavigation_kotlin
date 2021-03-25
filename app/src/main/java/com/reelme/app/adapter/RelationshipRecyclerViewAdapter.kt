package com.reelme.app.adapter

import com.reelme.app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.databinding.RelationshipItemLayoutBinding
import com.reelme.app.listeners.RelationshipEventListener
import com.reelme.app.model.Flight
import com.reelme.app.pojos.RelationshipStatus


class RelationshipRecyclerViewAdapter(flsLst: List<RelationshipStatus>, ctx: Context) : RecyclerView.Adapter<RelationshipRecyclerViewAdapter.ViewHolder>(), RelationshipEventListener {
    private val flightsList: List<RelationshipStatus>
    private val context: Context
    var selectedPosition=-1;
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding: RelationshipItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.relationship_item_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight: RelationshipStatus = flightsList[position]
        holder.flightItemBinding.flight = flight
        holder.flightItemBinding.itemClickListener = this
        holder.flightItemBinding.itemPosition = position

        holder.flightItemBinding.isValid = selectedPosition==position

    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

    inner class ViewHolder(flightItemLayoutBinding: RelationshipItemLayoutBinding) : RecyclerView.ViewHolder(flightItemLayoutBinding.getRoot()) {
        var flightItemBinding: RelationshipItemLayoutBinding

        init {
            flightItemBinding = flightItemLayoutBinding
        }
    }

    init {
        flightsList = flsLst
        context = ctx
    }



    override fun bookFlight(f: RelationshipStatus, view: View?, itemPosition: Int) {
//        Toast.makeText(context, "You booked " + f.relationship,
//                Toast.LENGTH_LONG).show()
        selectedPosition = itemPosition
        notifyDataSetChanged()
    }

    fun getSelectedItem() = selectedPosition

}