package com.reelme.app.adapter

import com.reelme.app.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.databinding.FlightItemLayoutBinding
import com.reelme.app.listeners.FlightsEventListener
import com.reelme.app.pojos.Gender


class FlightsRecyclerViewAdapter(flsLst: List<Gender>, ctx: Context) : RecyclerView.Adapter<FlightsRecyclerViewAdapter.ViewHolder>(), FlightsEventListener {
    private val flightsList: List<Gender> = flsLst
    private val context: Context = ctx
    var selectedPosition = -1;
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding: FlightItemLayoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.flight_item_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight: Gender = flightsList[position]
        holder.flightItemBinding.flight = flight
        holder.flightItemBinding.itemClickListener = this
        holder.flightItemBinding.itemPosition = position
        holder.flightItemBinding.isValid = selectedPosition == position

    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

    inner class ViewHolder(flightItemLayoutBinding: FlightItemLayoutBinding) : RecyclerView.ViewHolder(flightItemLayoutBinding.root) {
        var flightItemBinding: FlightItemLayoutBinding = flightItemLayoutBinding
    }


    override fun bookFlight(f: Gender, view: View?, itemPosition: Int) {
      //  Toast.makeText(context, "You chose Gender" + f.gender, Toast.LENGTH_LONG).show()
        Log.d( "FlightsRecyclerViewAda" , f.gender)

        selectedPosition = itemPosition
        notifyDataSetChanged()
    }

    fun getSelectedItem() = selectedPosition


}