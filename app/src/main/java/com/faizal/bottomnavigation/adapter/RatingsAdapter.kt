package com.faizal.bottomnavigation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.faizal.bottomnavigation.databinding.RatingItemBinding
import com.faizal.bottomnavigation.viewmodel.RequestCompleteViewModel

class RatingsAdapter() : RecyclerView.Adapter<RatingsAdapter.UserHolder>() {

    companion object {
        private val TAG = "GameChooserAdapter"
    }
    fun setModel(model: RequestCompleteViewModel) {
        profileInfoViewModel = model;
    }

    fun setData(items: List<String>) {
        userIds = items
        notifyDataSetChanged()
    }

    var userIds = emptyList<String>()

    lateinit var profileInfoViewModel : RequestCompleteViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RatingItemBinding.inflate(inflater,parent,false);
        return UserHolder(view)
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.binding.userText.text = userIds.get(position)
        holder.binding.gameChooserModel = profileInfoViewModel
    }

    class UserHolder(internal val binding: RatingItemBinding) : RecyclerView.ViewHolder(binding.root)

}