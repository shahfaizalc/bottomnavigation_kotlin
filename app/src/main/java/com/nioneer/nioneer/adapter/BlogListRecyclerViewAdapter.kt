package com.nioneer.nioneer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nioneer.nioneer.R
import com.nioneer.nioneer.databinding.ListItemBlogBinding
import com.nioneer.nioneer.listeners.BlogListEventListener
import com.nioneer.nioneer.listeners.ShareEventListener
import com.nioneer.nioneer.rss.utils.Constantss
import com.nioneer.nioneer.rss.utils.HasNetwork
import com.nioneer.nioneer.rss.utils.ShareLink
import com.nioneer.nioneer.viewmodel.RssHomeViewModel
import javax.inject.Inject


/**
 * Blog recycler view adapter class is to view list of blog articles.
 */
class BlogListRecyclerViewAdapter(private val rssHomeViewModel: RssHomeViewModel) :
    RecyclerView.Adapter<BlogListRecyclerViewAdapter.ViewHolder>(), BlogListEventListener, ShareEventListener {

    /**
     * TAG : class name
     */
    private val TAG = "BlogListRecyclerView"

    /**
     * Context
     */
    lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        this.context = viewGroup.context
        return ViewHolder( LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_blog, viewGroup, false)
    )
}

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) { viewHolder.binding!!.simpleListAdapter = this
        with(viewHolder.binding!!) {
            blogItemModel = rssHomeViewModel.blogArticlesFilteredListModel[position]
            itemPosition = position
            mainDataModel = rssHomeViewModel
          //  markAsRead = getMarkAsReadState(rssHomeViewModel.blogArticlesFilteredListModel[position].link);
            executePendingBindings()
        }
        viewHolder.binding!!.itemStateClickListener = this
        viewHolder.binding!!.itemClickListener = this
    }

    override fun getItemCount() = rssHomeViewModel.blogArticlesFilteredListModel.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ListItemBlogBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    /**
     * On Blog list item click
     * @param rssHomeViewModel : Rss home view model
     * @param position : item position
     */
    override fun onClickBlogListItem(rssHomeViewModel: RssHomeViewModel, position: Int) {
        when (HasNetwork.isConnected(context)) {
            true -> {
                Log.d(TAG, "onClickBlogListItem: " + rssHomeViewModel.blogArticlesFilteredListModel[position].title)
                rssHomeViewModel.doUpdateClickedItem(rssHomeViewModel.blogArticlesFilteredListModel[position])
                setMarkAsRead(rssHomeViewModel.blogArticlesFilteredListModel[position].link)
                notifyItemChanged(position)
            }
            false -> {
                Log.d(TAG, "Network unavailable ") //Error msg shown to user through network handler by activity
            }
        }
    }

    /**
     * On Blog list item share clicked
     * @param rssHomeViewModel : Rss home view model
     * @param position : item position
     */
    override fun onClickShareItem(listModel: RssHomeViewModel, position: Int) {
        Log.d(TAG, "onClickShareItem: clicked")
        val shareLink = ShareLink()
        val intent = shareLink.shareArticleLink(
            context.resources.getString(R.string.shareInfo),
            rssHomeViewModel.blogArticlesFilteredListModel[position].title,
            rssHomeViewModel.blogArticlesFilteredListModel[position].link
        )
        shareLink.openChooser(intent, context)
    }

    /**
     * Check the list item has been read already. If yes mark it as read.
     * @param keyPref: Preference key name
     */
//    private fun getMarkAsReadState(keyPref: String): Drawable {
//        val prefs = context.getSharedPreferences(Constants.MARK_AS_READ_PREF, Context.MODE_PRIVATE)
//        val isMarkedAsRead = prefs.getBoolean(keyPref, false)
//        when (isMarkedAsRead) {
//            true -> return context.resources.getDrawable(R.drawable.check_circle_read, null)
//            false -> return context.resources.getDrawable(R.drawable.check_circle_unread, null)
//        }
//    }

    /**
     * When user clicks update item as marked.
     * @param keyPref: Preference key name
     */
    private fun setMarkAsRead(keyPref: String) {
        Log.d(TAG, "setMarkAsRead: true")
        val prefs = context.getSharedPreferences(Constantss.MARK_AS_READ_PREF, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(keyPref, true)
        editor.apply()
    }
}
