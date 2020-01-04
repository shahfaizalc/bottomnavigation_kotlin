package com.guiado.grads.adapter

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.FacebookSdk.getApplicationContext
import com.guiado.grads.R
import com.guiado.grads.databinding.ListItemMygroupsBinding
import com.guiado.grads.listeners.MyGroupsEventListener
import com.guiado.grads.util.convertLongToTime
import com.guiado.grads.util.getDiscussionKeys
import com.guiado.grads.viewmodel.MyGroupsModel


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
            keyWordsTag = getDiscussionKeys(groupsViewModel!!.keyWords,viewHolder.itemView.context)
            postDate= viewModel.talentProfilesList[position].postedDate?.toLong()?.let { convertLongToTime(it) }
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
        popup = PopupMenu(context, viewHolder.itemView)
        popup.gravity = Gravity.RIGHT
        popup.getMenuInflater().inflate(R.menu.menu_paging, popup.getMenu())

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
