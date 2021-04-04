package com.reelme.app.adapter


import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.reelme.app.R
import com.reelme.app.viewmodel.SalesViewModel
import com.squareup.picasso.Picasso


/**
 * To setup view pager
 * @param tabLayout
 * @param viewPager
 */
@BindingAdapter("setUpWithViewpager")
fun setUpWithViewpager(tabLayout: TabLayout, viewPager: ViewPager) {
    viewPager.addOnAdapterChangeListener { viewPager1, oldAdapter, newAdapter ->
        Log.d("TAG", "onAdapterChanged")
        if (!(oldAdapter == null && newAdapter == null)) {
            tabLayout.setupWithViewPager(viewPager1)
        }
    }
}

/**
 *
 * @param view Image view
 * @param imageUrl image url
 */
@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).into(view)
}

/**
 * To show the list of sales items in recyclerview
 * @param salesViewModel : View model of sales items lists
 * @param recyclerView       : List of sales items recycler view
 */
@BindingAdapter("app:salesListAdapter")
fun adapter(recyclerView: RecyclerView, salesViewModel: SalesViewModel) {
     val columns = recyclerView.context.getResources().getInteger(R.integer.gallery_columns);

    val linearLayoutManager = StaggeredGridLayoutManager(columns,  GridLayoutManager.VERTICAL)
    recyclerView.layoutManager = linearLayoutManager
    val listAdapter = SaleItemsAdapter(salesViewModel)
    recyclerView.adapter = listAdapter
    salesViewModel.saleItemsAdapter = listAdapter
    salesViewModel.linearLayoutManager= linearLayoutManager
}


