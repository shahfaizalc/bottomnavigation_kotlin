package com.guiado.grads

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
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.adapter.*
import com.guiado.grads.handler.*
import com.guiado.grads.model.CoachItem
import com.guiado.grads.model.CountriesInfoModel
import com.guiado.grads.model.IndiaItem
import com.guiado.grads.model.SearchMode
import com.guiado.grads.model2.PostDiscussion
import com.guiado.grads.util.notNull
import com.guiado.grads.viewmodel.*
import com.squareup.picasso.Picasso








@BindingAdapter("rating")
fun setRating(ratingBar: RatingBar, ratings: RequestCompleteViewModel) {
    ratingBar.rating = ratings.ratings
    ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
        ratings.ratings = rating
    }
}

@BindingAdapter("autoAdapter")
fun setAdapter(view: AutoCompleteTextView, pArrayAdapter: AdressViewModel) {
    //  val autoFillTextAdapter = ProductListAdapter(view.context, pArrayAdapter.roleAdapter!!)
    val address = pArrayAdapter.roleAdapterAddress;
    val autoText = view;
    val autoFillTextAdapter = PeopleAdapter(view.context, R.layout.autofilitem, R.id.text_title, address)

    autoText.setAdapter(autoFillTextAdapter)
   // autoText.setText("")
    autoText.onItemClickListener = object : OnItemSelectedListener, AdapterView.OnItemClickListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.d("user", "slecgt" + autoFillTextAdapter.suggestions.get(position))
        }

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          //  Toast.makeText(parent!!.context, "Spinner item 1! " + autoFillTextAdapter.suggestions.get(position), Toast.LENGTH_SHORT).show()
            Log.d("user", "slecgt" + autoFillTextAdapter.suggestions.get(position))
            pArrayAdapter.cityCode = autoFillTextAdapter.suggestions.get(position).citycode
            pArrayAdapter.city = autoFillTextAdapter.suggestions.get(position).cityname
        }
    }
}

//@BindingAdapter("focusId", "focusTarget")
//fun requestFocusFromTouch(view: View, id: Int, target: Int) {
//    if (target == 0 || id != target) {
//        return
//    }
//    view.requestFocusFromTouch()
//}
//
//


@BindingAdapter("autoAdapter")
fun setAdapter(view: AutoCompleteTextView, pArrayAdapter: AdSearchModel) {
    //  val autoFillTextAdapter = ProductListAdapter(view.context, pArrayAdapter.roleAdapter!!)
    val address = pArrayAdapter.roleAdapterAddress;
    val autoText = view;
    val autoFillTextAdapter = PeopleAdapter(view.context, R.layout.autofilitem, R.id.text_title, address)

    autoText.setAdapter(autoFillTextAdapter)
    autoText.setText("")
    autoText.onFocusChangeListener = View.OnFocusChangeListener { view, b ->

        Log.d("user", "citycodechange")

        for (strr in address!!) {
            Log.d("user", "citycodechange" + strr.cityname + " " + autoText.text.toString())

            if (strr.cityname.equals(autoText.text.toString())) {
                pArrayAdapter.cityCode = strr.citycode
                break
            } else {
                pArrayAdapter.cityCode = "0"

            }
        }

    }
}


@BindingAdapter( "app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: AdSearchModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = SearchTalentsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreTalentsHandler(countriesViewModel, listAdapter)

    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
    bindingAdapter.initRequest(recyclerView)

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
}


@BindingAdapter( "app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: SimilarDiscussionModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = SimilarAdsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreSimilarAdsHandler(countriesViewModel, listAdapter)


    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)

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
            bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
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
}


@BindingAdapter( "app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: MyAdsModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = MyAdsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreMyAdsHandler(countriesViewModel, listAdapter)


    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
    bindingAdapter.initRequest(recyclerView)

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
            bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
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
}


@BindingAdapter( "app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView,countriesViewModel: MyDiscussionModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = MyDiscussionAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreMyDiscussionHandler(countriesViewModel, listAdapter)
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
            bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
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
                countriesViewModel.doGetTalentsSearch(s)
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
            }
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {
            if(strQuery.isNullOrEmpty()){
                countriesViewModel.doGetTalents()
            }
            return false
        }
    })
}



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

@BindingAdapter( "app:searchRecycler2")
fun adapter2(recyclerView: RecyclerView, countriesViewModel: AdSearchModel) {


}

//@BindingAdapter("adapterFlex")
//fun loadAdapterFlex2(recyclerView: RecyclerView, pArrayAdapter: PostAdViewModel) {
//    val catergories = pArrayAdapter.singleAttribute!!.category
//    if (catergories.size > 0) {
//        val layoutManager = FlexboxLayoutManager(recyclerView.context)
//        layoutManager.flexDirection = FlexDirection.ROW
//        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
//        layoutManager.alignItems = AlignItems.FLEX_START
//        recyclerView.layoutManager = layoutManager
//        val adapter = CategoryFlexboxAdapter(recyclerView.context, catergories, pArrayAdapter)
//        recyclerView.adapter = adapter
//    }
//}


@BindingAdapter("app:data")
fun loadAdapterx(recyclerView: RecyclerView, profileInfoViewModel: UserViewModel) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    val adapter = UserAdapter()
    recyclerView.adapter = adapter
    (recyclerView.adapter as UserAdapter).setData(profileInfoViewModel.userIds)
}

@BindingAdapter("app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView, profileInfoViewModel: GameChooserModel, recyclerView: RecyclerView) {

    val emptySting = "";
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    val adapter = GameChooserAdapter()
    recyclerView.adapter = adapter
    (recyclerView.adapter as GameChooserAdapter).setModel(profileInfoViewModel)
    (recyclerView.adapter as GameChooserAdapter).setData(profileInfoViewModel.listOfCoachings)

    // Search view clear query
    val searchClear = searchView.findViewById<View>(R.id.search_close_btn)
    searchClear.setOnClickListener({ searchView.setQuery(emptySting, true) })

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(query: String): Boolean {
            if (!query.isEmpty()) {
                //Kotlin filter to filter the query results
                val model =
                        profileInfoViewModel.listOfCoachings!!.filter {
                            it.categoryname.toLowerCase().startsWith(query.toLowerCase())
                        }

                (recyclerView.adapter as GameChooserAdapter).setData(model as ArrayList<CoachItem> )

            } else {
                (recyclerView.adapter as GameChooserAdapter).setData(profileInfoViewModel.listOfCoachings)

            }
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



@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, profileInfoViewModel: RequestCompleteViewModel ) {

    val emptySting = "";
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    val adapter = RatingsAdapter()
    recyclerView.adapter = adapter
    (recyclerView.adapter as RatingsAdapter).setModel(profileInfoViewModel)
   // (recyclerView.adapter as RatingsAdapter).setData(profileInfoViewModel.userIds)

    profileInfoViewModel.adapter = recyclerView.adapter as RatingsAdapter

}


@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    val i = TextUtils.isEmpty(imageUrl)


    if (i) {
        view.setImageDrawable(view.getContext().getDrawable(R.drawable.placeholder_profile))
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


//@BindingAdapter("app:broken","app:position","app:viewModel")
//fun loadImage(view: ImageView, imageUrl: GroupsModel, position: Int,postDiscussion: Groups) {
//
//    view.isSelected = imageUrl.isBookmarked(postDiscussion)!!
//    postDiscussion.members.notNull {  }
//    view.setOnClickListener({ it.isSelected = !it.isSelected })
//
//}


@BindingAdapter( "app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView,countriesViewModel: SavedDiscussionModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = SavedDiscussionAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreSavedDiscussionHandler(countriesViewModel, listAdapter)


    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)

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
                countriesViewModel.doGetTalentsSearch(s)
                bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
            }
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {
            if(strQuery.isNullOrEmpty()){
                countriesViewModel.doGetTalents()
            }
            return false
        }
    })
}





