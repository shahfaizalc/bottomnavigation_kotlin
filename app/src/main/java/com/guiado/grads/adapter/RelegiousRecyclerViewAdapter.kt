package com.guiado.grads.adapter

import com.guiado.grads.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.databinding.RelationshipItemLayoutBinding
import com.guiado.grads.databinding.RelegiousItemLayoutBinding
import com.guiado.grads.listeners.RelationshipEventListener
import com.guiado.grads.listeners.RelegiousEventListener
import com.guiado.grads.model.Flight


class RelegiousRecyclerViewAdapter(flsLst: List<Flight>, ctx: Context) : RecyclerView.Adapter<RelegiousRecyclerViewAdapter.ViewHolder>(), RelegiousEventListener {
    private val flightsList: List<Flight>
    private val context: Context
    var selectedPosition=-1;
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding: RelegiousItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.relegious_item_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight: Flight = flightsList[position]
        holder.flightItemBinding.flight = flight
        holder.flightItemBinding.itemClickListener = this
        holder.flightItemBinding.itemPosition = position

        holder.flightItemBinding.isValid = selectedPosition==position

    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

    inner class ViewHolder(flightItemLayoutBinding: RelegiousItemLayoutBinding) : RecyclerView.ViewHolder(flightItemLayoutBinding.getRoot()) {
        var flightItemBinding: RelegiousItemLayoutBinding

        init {
            flightItemBinding = flightItemLayoutBinding
        }
    }

    init {
        flightsList = flsLst
        context = ctx
    }



    override fun bookFlight(f: Flight, view: View?, itemPosition: Int) {
        Toast.makeText(context, "You booked " + f.pincode,
                Toast.LENGTH_LONG).show()
        selectedPosition = itemPosition
        notifyDataSetChanged()
    }

}