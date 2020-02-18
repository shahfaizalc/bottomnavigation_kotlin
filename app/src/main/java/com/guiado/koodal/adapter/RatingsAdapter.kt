package com.guiado.koodal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guiado.koodal.databinding.RatingItemBinding
import com.guiado.koodal.model2.Reviews
import com.guiado.koodal.util.convertLongToTime
import com.guiado.koodal.util.notNull
import com.guiado.koodal.viewmodel.RequestCompleteViewModel

class RatingsAdapter() : RecyclerView.Adapter<RatingsAdapter.UserHolder>() {

    companion object {
        private val TAG = "GameChooserAdapter"
    }
    fun setModel(model: RequestCompleteViewModel) {
        profileInfoViewModel = model;
    }

    fun setData(items: List<Reviews>) {
        userIds = items
        notifyDataSetChanged()
    }

    var userIds = emptyList<Reviews>()

    lateinit var profileInfoViewModel : RequestCompleteViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RatingItemBinding.inflate(inflater,parent,false);
        return UserHolder(view)
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.binding.userText.text = userIds.get(position).userId
        holder.binding.review.text = userIds.get(position).review
        holder.binding.date.text =  convertLongToTime( userIds.get(position).date!!.toLong())
        userIds.get(position).rating.notNull { holder.binding.rates.rating  = it.toFloat() }

        holder.binding.gameChooserModel = profileInfoViewModel
    }

    class UserHolder(internal val binding: RatingItemBinding) : RecyclerView.ViewHolder(binding.root)

}