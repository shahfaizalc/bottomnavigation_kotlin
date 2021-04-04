package com.reelme.app

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.adapter.*
import com.reelme.app.handler.*
import com.reelme.app.model.CountriesInfoModel
import com.reelme.app.model.SearchMode
import com.reelme.app.viewmodel.*
import com.squareup.picasso.Picasso


@BindingAdapter( "app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView ,countriesViewModel: GoalModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = GoalAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreGoalHandler(countriesViewModel, listAdapter)
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)


    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    countriesViewModel.talentProfilesList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<CountriesInfoModel>>() {
        override fun onItemRangeRemoved(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach1")
        }

        override fun onItemRangeMoved(sender: ObservableList<CountriesInfoModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            Log.d("rach", "rach2")
        }

        override fun onItemRangeInserted(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach3")
            bindingAdapter.resetRecycleView(recyclerView)
            if(countriesViewModel.resetScrrollListener) {
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
                countriesViewModel.resetScrrollListener = false
            }

        }

        override fun onItemRangeChanged(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach4")
            bindingAdapter.resetRecycleView(recyclerView)
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
            bindingAdapter.resetRecycleView(recyclerView)
        }

    });

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String?): Boolean {
            if(s.isNullOrEmpty()){
                countriesViewModel.doGetTalents()
            } else {
                if(countriesViewModel.searchMode.ordinal == SearchMode.DEFAULT.ordinal){
                    countriesViewModel.searchMode = SearchMode.SEARCH
                } else if(countriesViewModel.searchMode.ordinal == (SearchMode.CATEGORY.ordinal)){
                    countriesViewModel.searchMode = SearchMode.CATEGORYANDSEARCH
                }
                //   countriesViewModel.doGetTalentsSearch(s)
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {
//            if(strQuery.isNullOrEmpty()){
//                if(countriesViewModel.searchMode.ordinal == SearchMode.SEARCH.ordinal){
//                    countriesViewModel.searchMode = SearchMode.DEFAULT
//                } else if(countriesViewModel.searchMode.ordinal == (SearchMode.CATEGORYANDSEARCH.ordinal)){
//                    countriesViewModel.searchMode = SearchMode.CATEGORY
//                }
//                countriesViewModel.doGetTalents()
//
//            }
            return false
        }
    })
}


@BindingAdapter( "app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView ,countriesViewModel: ChallengeModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = ChallengeAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreChallengeHandler(countriesViewModel, listAdapter)
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)


    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    countriesViewModel.talentProfilesList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<CountriesInfoModel>>() {
        override fun onItemRangeRemoved(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach1")
        }

        override fun onItemRangeMoved(sender: ObservableList<CountriesInfoModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            Log.d("rach", "rach2")
        }

        override fun onItemRangeInserted(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach3")
            bindingAdapter.resetRecycleView(recyclerView)
            if(countriesViewModel.resetScrrollListener) {
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
                countriesViewModel.resetScrrollListener = false
            }

        }

        override fun onItemRangeChanged(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach4")
            bindingAdapter.resetRecycleView(recyclerView)
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
            bindingAdapter.resetRecycleView(recyclerView)
        }

    });

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String?): Boolean {
            if(s.isNullOrEmpty()){
                countriesViewModel.doGetTalents()
            } else {
                if(countriesViewModel.searchMode.ordinal == SearchMode.DEFAULT.ordinal){
                    countriesViewModel.searchMode = SearchMode.SEARCH
                } else if(countriesViewModel.searchMode.ordinal == (SearchMode.CATEGORY.ordinal)){
                    countriesViewModel.searchMode = SearchMode.CATEGORYANDSEARCH
                }
                //   countriesViewModel.doGetTalentsSearch(s)
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {
//            if(strQuery.isNullOrEmpty()){
//                if(countriesViewModel.searchMode.ordinal == SearchMode.SEARCH.ordinal){
//                    countriesViewModel.searchMode = SearchMode.DEFAULT
//                } else if(countriesViewModel.searchMode.ordinal == (SearchMode.CATEGORYANDSEARCH.ordinal)){
//                    countriesViewModel.searchMode = SearchMode.CATEGORY
//                }
//                countriesViewModel.doGetTalents()
//
//            }
            return false
        }
    })
}

@BindingAdapter( "app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView ,countriesViewModel: DiscussionModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = DiscussionAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreDiscussionHandler(countriesViewModel, listAdapter)
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)


    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    countriesViewModel.talentProfilesList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<CountriesInfoModel>>() {
        override fun onItemRangeRemoved(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach1")
        }

        override fun onItemRangeMoved(sender: ObservableList<CountriesInfoModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            Log.d("rach", "rach2")
        }

        override fun onItemRangeInserted(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach3")
            bindingAdapter.resetRecycleView(recyclerView)
            if(countriesViewModel.resetScrrollListener) {
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
                countriesViewModel.resetScrrollListener = false
            }

        }

        override fun onItemRangeChanged(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach4")
            bindingAdapter.resetRecycleView(recyclerView)
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
            bindingAdapter.resetRecycleView(recyclerView)
        }

    });

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String?): Boolean {
            if(s.isNullOrEmpty()){
                countriesViewModel.doGetTalents()
            } else {
                if(countriesViewModel.searchMode.ordinal == SearchMode.DEFAULT.ordinal){
                    countriesViewModel.searchMode = SearchMode.SEARCH
                } else if(countriesViewModel.searchMode.ordinal == (SearchMode.CATEGORY.ordinal)){
                    countriesViewModel.searchMode = SearchMode.CATEGORYANDSEARCH
                }
             //   countriesViewModel.doGetTalentsSearch(s)
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {
//            if(strQuery.isNullOrEmpty()){
//                if(countriesViewModel.searchMode.ordinal == SearchMode.SEARCH.ordinal){
//                    countriesViewModel.searchMode = SearchMode.DEFAULT
//                } else if(countriesViewModel.searchMode.ordinal == (SearchMode.CATEGORYANDSEARCH.ordinal)){
//                    countriesViewModel.searchMode = SearchMode.CATEGORY
//                }
//                countriesViewModel.doGetTalents()
//
//            }
            return false
        }
    })
}




    @BindingAdapter("app:clickableString")
fun loadAdapterx(textView: TextView, profileInfoViewModel: RegistrationModel) {
    val ss = SpannableString(textView.getText())

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            Log.d("tag", "onSpecialClick: ")
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true
            ds.color = Color.BLUE
        }
    }

    ss.setSpan(clickableSpan, 36, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.setMovementMethod(LinkMovementMethod.getInstance())
    textView.setText(ss)

    textView.setOnClickListener { view ->  profileInfoViewModel.termsAndCondition()}



}




@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    val i = TextUtils.isEmpty(imageUrl)

    Log.d("tag", "onSpecialClick: $imageUrl onSpecialClick $i")

    if (i) {
        view.setImageDrawable(ContextCompat.getDrawable(view.context,R.drawable.placeholder_profile))
    } else {
        Picasso.get()
                .load(imageUrl)
                .resize(50, 50)
                .error(R.drawable.placeholder_profile)
                .placeholder(R.drawable.placeholder_profile)
                .into(view)
    }
}

@BindingAdapter("app:viewModel","app:position")
fun loadImage(view: ImageView, imageUrl: DiscussionModel, position: Int) {

   // view.isSelected = imageUrl.isBookmarked(postDiscussion)!!
    view.setOnClickListener { it.isSelected = !it.isSelected }

}


@BindingAdapter("app:viewModel","app:position")
fun loadImage(view: ImageView, imageUrl: ChallengeModel, position: Int) {

    // view.isSelected = imageUrl.isBookmarked(postDiscussion)!!
    view.setOnClickListener { it.isSelected = !it.isSelected }

}










