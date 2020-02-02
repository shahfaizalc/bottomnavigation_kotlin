package com.guiado.linkify.adapter

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.linkify.R
import com.guiado.linkify.databinding.ListItemMygroupsBinding
import com.guiado.linkify.listeners.MyGroupsEventListener
import com.guiado.linkify.util.getDiscussionCategories
import com.guiado.linkify.viewmodel.MyGroupsModel


/**
 * Country recycler view adapter to view list of items
 */
class MyGroupsAdapter(private val adSearchModel: MyGroupsModel) :
        MyGroupsEventListener, RecyclerView.Adapter<MyGroupsAdapter.ViewHolder>(){

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context:Context
    lateinit var popup : PopupMenu

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int)
            = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_mygroups,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        context = viewHolder.itemView.context
        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            groupsViewModel = viewModel.talentProfilesList[position]
            keyWordsTag = getDiscussionCategories(groupsViewModel!!.keyWords,viewHolder.itemView.context)
            itemPosition = position
            mainDataModel = viewModel
            viewDot = showMenu(viewModel, position)
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
        popup = PopupMenu(context, viewHolder.itemView)
        popup.gravity = Gravity.RIGHT
        popup.getMenuInflater().inflate(R.menu.menu_paging, popup.getMenu())

    }

    private fun showMenu(viewModel: MyGroupsModel, position: Int): Int {
        if (viewModel.talentProfilesList[position].postedBy.equals(adSearchModel.mAuth.currentUser?.uid)) {
            return  View.INVISIBLE
        }
        return  View.VISIBLE
    }


    override fun getItemCount()= adSearchModel.talentProfilesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemMygroupsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: MyGroupsModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
        countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position],position)

    }

    override fun onClickAdSearchListItemDot(countriesViewModel: MyGroupsModel, position: Int) {
        Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)

        popup.show()

        popup.setOnMenuItemClickListener {
            countriesViewModel.leaveGroup(countriesViewModel.talentProfilesList[position],position)
            false
        }


    }
}
