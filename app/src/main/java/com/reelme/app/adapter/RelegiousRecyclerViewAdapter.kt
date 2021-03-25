package com.reelme.app.adapter

import com.reelme.app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.databinding.RelegiousItemLayoutBinding
import com.reelme.app.listeners.RelegiousEventListener
import com.reelme.app.model.Flight
import com.reelme.app.pojos.ReligiousBelief


class RelegiousRecyclerViewAdapter(flsLst: List<ReligiousBelief>, ctx: Context) : RecyclerView.Adapter<RelegiousRecyclerViewAdapter.ViewHolder>(), RelegiousEventListener {
    private val flightsList: List<ReligiousBelief>
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
        val flight: ReligiousBelief = flightsList[position]
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



    override fun bookFlight(f: ReligiousBelief, view: View?, itemPosition: Int) {
//        Toast.makeText(context, "You booked " + f.religious,
//                Toast.LENGTH_LONG).show()
        selectedPosition = itemPosition
        notifyDataSetChanged()
    }

    fun getSelectedItem() = selectedPosition

}