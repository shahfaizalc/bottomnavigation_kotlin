package com.faizal.bottomnavigation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.model.CategoryItem
import com.faizal.bottomnavigation.viewmodel.PostAdViewModel

class CategoryFlexboxAdapter(private val context: Context, private val stringArrayList: List<CategoryItem>, internal var pArrayAdapter: PostAdViewModel)
    : RecyclerView.Adapter<CategoryFlexboxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.hobbies_flexlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = stringArrayList[position].name
        holder.title.setOnClickListener {
            System.out.println("hello")
            pArrayAdapter.itemPositionCategory = position
        }
    }

    override fun getItemCount(): Int {
        return stringArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(R.id.tvTitle)
        }

    }
}
