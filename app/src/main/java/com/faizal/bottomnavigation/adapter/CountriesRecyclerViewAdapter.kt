package com.faizal.bottomnavigation.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.ListItemCountryBinding
import com.faizal.bottomnavigation.listeners.CountriesEventListener
import com.faizal.bottomnavigation.util.getAddress
import com.faizal.bottomnavigation.util.offerPrice
import com.faizal.bottomnavigation.viewmodel.AdSearchModel

/**
 * Country recycler view adapter to view list of items
 */
class CountriesRecyclerViewAdapter(private val countriesViewModel: AdSearchModel) :
        CountriesEventListener,RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_country,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = countriesViewModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.countriesInfoModelFilter[position]
            priceActual = ""+countriesInfoModel!!.price+" x "+countriesInfoModel!!.price
            priceDiscount = ""+countriesInfoModel!!.discount
            priceTotal = offerPrice(countriesInfoModel!!)
            address = getAddress(countriesInfoModel!!.address)
            balanceTicket = countriesInfoModel!!.balanceTicket.toString()
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
    }

    override fun getItemCount()= countriesViewModel.countriesInfoModelFilter.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemCountryBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickCountriesListItem(countriesViewModel: AdSearchModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.countriesInfoModelFilter[position].address!!.city)
        countriesViewModel.openFragment(countriesViewModel.countriesInfoModelFilter[position])

    }
}
