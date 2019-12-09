package com.faizal.bottomnavigation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faizal.bottomnavigation.databinding.ListItemBinding
import com.faizal.bottomnavigation.listeners.ChooserEventListener
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.viewmodel.GameChooserModel

class GameChooserAdapter() : RecyclerView.Adapter<GameChooserAdapter.UserHolder>() , ChooserEventListener{

    companion object {
        private val TAG = "GameChooserAdapter"
    }
    fun setModel(model: GameChooserModel) {
        profileInfoViewModel = model;
    }

    fun setData(items: ArrayList<CoachItem>?) {
        if (items != null) {
            userIds = items
        }
        notifyDataSetChanged()
    }

    var userIds = ArrayList<CoachItem>()

    lateinit var profileInfoViewModel : GameChooserModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListItemBinding.inflate(inflater,parent,false);
        return UserHolder(view)
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.binding.userText.text = userIds.get(position).categoryname
        holder.binding.itemPosition = position
        holder.binding.listener = this
        holder.binding.gameChooserModel = profileInfoViewModel
    }

    class UserHolder(internal val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClickAdSearchListItem(countriesViewModel: GameChooserModel, position: Int) {
        Log.d(TAG,"Category "+countriesViewModel.listOfCoachings!!.get(position))

        countriesViewModel.onNextButtonClick(position)
    }
}