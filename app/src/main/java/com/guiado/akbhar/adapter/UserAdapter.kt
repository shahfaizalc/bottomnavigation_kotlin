package com.guiado.akbhar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    fun setData(items: List<String>) {
        userIds = items
        notifyDataSetChanged()
    }

    var userIds = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userIds.get(position))
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userId: String) {
            itemView.userText.text = "$userId"

        }
    }
}