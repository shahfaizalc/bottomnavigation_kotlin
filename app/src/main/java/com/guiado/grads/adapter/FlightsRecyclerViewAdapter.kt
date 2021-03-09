package com.guiado.grads.adapter

import com.guiado.grads.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.databinding.FlightItemLayoutBinding
import com.guiado.grads.listeners.FlightsEventListener
import com.guiado.grads.model.Flight


class FlightsRecyclerViewAdapter(flsLst: List<Flight>, ctx: Context) : RecyclerView.Adapter<FlightsRecyclerViewAdapter.ViewHolder>(), FlightsEventListener {
    private val flightsList: List<Flight>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding: FlightItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.flight_item_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight: Flight = flightsList[position]
        holder.flightItemBinding.setFlight(flight)
        holder.flightItemBinding.setItemClickListener(this)
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

    override fun bookFlight(f: Flight) {
        Toast.makeText(context, "You booked " + f.pincode,
                Toast.LENGTH_LONG).show()
    }

    init {
        flightsList = flsLst
        context = ctx
    }
}