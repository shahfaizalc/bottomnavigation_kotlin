package com.guiado.grads.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.databinding.ListItemBinding
import com.guiado.grads.listeners.ChooserEventListener
import com.guiado.grads.model.CoachItem
import com.guiado.grads.viewmodel.GameChooserModel

class GameChooserAdapter() : RecyclerView.Adapter<GameChooserAdapter.UserHolder>() , ChooserEventListener{

    var selectedItem = -1;

    companion object {
        private val TAG = "GameChooserAdapter"
    }
    fun setModel(model: GameChooserModel) {
        profileInfoViewModel = model;
    }

    fun setData(items: ArrayList<CoachItem>?) {
        userIds = items!!
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
        holder.binding.userText2.visibility =  if(!position.equals(selectedItem))  View.INVISIBLE else View.VISIBLE
    }

    class UserHolder(internal val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClickAdSearchListItem(countriesViewModel: GameChooserModel, position: Int) {
        Log.d(TAG,"Category "+countriesViewModel.listOfCoachings!!.get(position))
        countriesViewModel.onNextButtonClick(position)
        selectedItem = position
        notifyDataSetChanged()
    }
}