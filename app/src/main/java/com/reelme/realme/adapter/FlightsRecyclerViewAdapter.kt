package com.reelme.realme.adapter

import com.reelme.realme.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reelme.realme.databinding.FlightItemLayoutBinding
import com.reelme.realme.listeners.FlightsEventListener
import com.reelme.realme.model.Flight


class FlightsRecyclerViewAdapter(flsLst: List<Flight>, ctx: Context) : RecyclerView.Adapter<FlightsRecyclerViewAdapter.ViewHolder>(), FlightsEventListener {
    private val flightsList: List<Flight>
    private val context: Context
    var selectedPosition=-1;
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding: FlightItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.flight_item_layout, parent, false)
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

    inner class ViewHolder(flightItemLayoutBinding: FlightItemLayoutBinding) : RecyclerView.ViewHolder(flightItemLayoutBinding.getRoot()) {
        var flightItemBinding: FlightItemLayoutBinding

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