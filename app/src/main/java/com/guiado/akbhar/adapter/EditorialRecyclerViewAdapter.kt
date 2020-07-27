package com.guiado.akbhar.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.ListEditorialItemBinding
import com.guiado.akbhar.databinding.ListItemMoroccoBinding
import com.guiado.akbhar.databinding.ListMagazinesItemBinding
import com.guiado.akbhar.listeners.EditorialEventListener
import com.guiado.akbhar.listeners.MagazineEventListener
import com.guiado.akbhar.listeners.MoroccoEventListener
import com.guiado.akbhar.model.Magazines
import com.guiado.akbhar.viewmodel.MagazineViewModel
import com.guiado.akbhar.viewmodel.MoroccoViewModel
import java.util.*
import kotlin.collections.ArrayList


/**
 * Country recycler view adapter to view list of items
 */
class EditorialRecyclerViewAdapter(private val adSearchModel: MagazineViewModel) :
        EditorialEventListener, RecyclerView.Adapter<EditorialRecyclerViewAdapter.ViewHolder>() {

    private val TAG = "ArtistRecyclerAdapter"

    lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_editorial_item,
            viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val viewModel = adSearchModel

        viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            countriesInfoModel = viewModel.talentProfilesList[position]
            //  keyWordsTag = getDiscussionCategories(countriesInfoModel!!.keyWords,viewHolder.itemView.context)
            // postDate= viewModel.talentProfilesList[position].postedDate?.toLong()?.let { convertLongToTime(it) }
            itemPosition = position
            mainDataModel = viewModel
            executePendingBindings()
        }
        viewHolder.binding!!.setItemClickListener(this)
    }


    override fun getItemCount() = adSearchModel.talentProfilesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListEditorialItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClickAdSearchListItem(countriesViewModel: MagazineViewModel, position: Int) {
        //   Log.d(TAG,"Click: "+ countriesViewModel.talentProfilesList[position].postedBy)
        // countriesViewModel.openFragment2(countriesViewModel.talentProfilesList[position])

    }

    override fun launchNews(countriesViewModel: MagazineViewModel, position: Int) {
        //   countriesViewModel.openFragment3(countriesViewModel.talentProfilesList[position],position)
    }
}
