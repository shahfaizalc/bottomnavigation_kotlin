package com.nioneer.nioneer.handlers

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nioneer.nioneer.R
import com.nioneer.nioneer.adapter.BlogListRecyclerViewAdapter
import com.nioneer.nioneer.rss.utils.RSSParser
import com.nioneer.nioneer.communication.RestHandler
import com.nioneer.nioneer.rss.utils.Constantss
import com.nioneer.nioneer.utils.EndlessRecyclerViewScrollListener
import com.nioneer.nioneer.viewmodel.RssHomeViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


/**
 *  Class to handle recyclerview scroll listener and to handle request and responses
 */
class RecyclerLoadItemsBlogHandler(private val rssHomeViewModel: RssHomeViewModel,
                                   private val listViewAdapterBlogList: BlogListRecyclerViewAdapter) {

    /**
     * TAG : class name
     */
    private val TAG = "RecyclerLoadItemsBlog"

    /**
     * Rest handler
     */
    lateinit var retrofitClientInstance: RestHandler

    /**
     * To load more items on list view scrolled
     */

    fun scrollListener(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {

        val listener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            }
        }
        recyclerView.addOnScrollListener(listener)
    }

    /**
     * Initiate the request
     * @param path subdirectory path
     * @param view RecylerView
     */
    fun initRequest(view: RecyclerView, path: String) {
        Log.d(TAG, "initRequest: sub url " + path)
        rssHomeViewModel.progressBarVisible = View.VISIBLE
        rssHomeViewModel.msgView = View.GONE
        launchRequest(path, view)
    }


    /**
     * Launch the request
     * @param urlPath domain subdirectory path
     * @param view Recyclerview
     */
    private fun launchRequest(urlPath: String, view: RecyclerView) {

        runBlocking {
            // handler manages the coroutine request and response.
            val handler = coroutineExceptionHandler(view)
            GlobalScope.launch(handler) {
                rssHomeViewModel.onLoadErrorMsgText = view.context.resources.getString(R.string.rsshome_hint)
                retrofitClientInstance= RestHandler()
                // initiate and make retrorfit request call
                val service = retrofitClientInstance.getServiceBlogArticles(Constantss.FAIRPHONE_BASE_URL, view.context)
                val repositories = withContext(Dispatchers.Default) {
                    service.retrieveBlogArticles(urlPath).await()
                }
                // receive and process the success response
                withContext(Dispatchers.Default) { coroutineSuccessHandler(repositories, view) }
                rssHomeViewModel.progressBarVisible = View.GONE
            }
        }

    }

    /**
     * Method to handle the server response. Iterate the feed in to model class to load in to recyclerview
     * @param response : response string
     * @param view : Recylerview
     */
    private fun coroutineSuccessHandler(response: String, view: RecyclerView) {
        Log.d(TAG, "coroutineSuccessHandler:success$response")
        rssHomeViewModel.onLoadErrorMsgVisibility = View.GONE
        val rSSParser = RSSParser()
        val elements = rSSParser.getRSSFeedItems(response)
        val curSize = listViewAdapterBlogList.itemCount
        rssHomeViewModel.blogArticlesListModel.addAll(elements)
        rssHomeViewModel.blogArticlesFilteredListModel = rssHomeViewModel.blogArticlesListModel
        notifyAdapter(view, curSize)
    }

    /**
     * Method to handle the request exception and to notify the failure to user.
     */
    private fun coroutineExceptionHandler(view: RecyclerView) = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG,"coroutineExceptionHandler"+exception.localizedMessage);
        rssHomeViewModel.progressBarVisible = View.GONE
        if (null != exception.message) {
            if (rssHomeViewModel.blogArticlesListModel.size < 1) {
                rssHomeViewModel.onLoadErrorMsgText= view.context.resources.getString(R.string.rssblog_list_retry)
                rssHomeViewModel.onLoadErrorMsgVisibility = View.VISIBLE
            } else {
                rssHomeViewModel.msgView = View.VISIBLE
                rssHomeViewModel.msg = exception.localizedMessage
            }
        }
    }

    /**
     * update results to recycler adapter
     * @param view : RecyclerView
     * @param curSize: list size
     *
     */
    fun notifyAdapter(view: RecyclerView, curSize: Int) {
        Log.d(TAG, "notifyadapter: list size" + curSize)
        view.post { listViewAdapterBlogList.notifyDataSetChanged() }
    }

    /**
     * Clear the adapter
     * @param recyclerView: RecyclerView
     */
    fun clearRecycleView(recyclerView: RecyclerView) {
        recyclerView.post { listViewAdapterBlogList.notifyItemRangeInserted(0, 0) }
    }

    /**
     * To reset recyclerview from filtered list.
     * @param recyclerView RecyclerView
     */
    fun resetRecycleView(recyclerView: RecyclerView) {
        rssHomeViewModel.blogArticlesFilteredListModel = rssHomeViewModel.blogArticlesListModel
        notifyAdapter(recyclerView,  rssHomeViewModel.blogArticlesFilteredListModel.size)
    }

}
