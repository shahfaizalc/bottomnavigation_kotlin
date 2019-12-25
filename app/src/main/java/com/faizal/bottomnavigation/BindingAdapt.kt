package com.faizal.bottomnavigation

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faizal.bottomnavigation.adapter.*
import com.faizal.bottomnavigation.handler.*
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model.CountriesInfoModel
import com.faizal.bottomnavigation.model2.Groups
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.util.notNull
import com.faizal.bottomnavigation.viewmodel.*
import com.squareup.picasso.Picasso


//@BindingAdapter("setUpWithViewpager")
//fun setUpWithViewpager(tabLayout: TabLayout, viewPager: ViewPager) {
//    viewPager.addOnAdapterChangeListener(ViewPager.OnAdapterChangeListener { viewPager, oldAdapter, newAdapter ->
//        if (oldAdapter == null && newAdapter == null) {
//            return@OnAdapterChangeListener
//        }
//        Log.i("TAG", "onAdapterChanged")
//        tabLayout.setupWithViewPager(viewPager)
//    })
//}
//
//@BindingAdapter("imageUrl")
//fun loadImage(view: ImageView, imageUrl: String) {
//    val i = TextUtils.isEmpty(imageUrl)
//    if (i) {
//        view.setImageDrawable(view.context.getDrawable(R.drawable.placeholder_profile))
//
//    } else {
//        Picasso.with(view.context)
//                .load(imageUrl)
//                .resize(240, 240)
//                .error(R.drawable.placeholder_profile)
//                .placeholder(R.drawable.placeholder_profile)
//                .into(view)
//    }
//}
//
//@BindingAdapter("adapterRecycle")
//fun loadAdapter(recyclerView: RecyclerView, profileInfoViewModel: ProfileInfoViewModel) {
//    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
//    val adapter = ProfileItemRecyclerAdpater(profileInfoViewModel.itemList, profileInfoViewModel.createItemListIcon())
//    recyclerView.adapter = adapter
//}
//
//@BindingAdapter("adapterFlex")
//fun loadAdapterFlex(recyclerView: RecyclerView, profileInfoViewModel: ProfileInfoViewModel) {
//    if (profileInfoViewModel.hobbiesList.size > 0) {
//        val layoutManager = FlexboxLayoutManager(recyclerView.context)
//        layoutManager.flexDirection = FlexDirection.ROW
//        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
//        layoutManager.alignItems = AlignItems.FLEX_START
//        recyclerView.layoutManager = layoutManager
//        val adapter = FlexboxAdapter(recyclerView.context, profileInfoViewModel.hobbiesList)
//        recyclerView.adapter = adapter
//    }
//}
//
//@BindingAdapter("autoAdapter")
//fun setAdapter(view: AutoCompleteTextView, pArrayAdapter: ProfileEditViewModel) {
//    //  val autoFillTextAdapter = ProductListAdapter(view.context, pArrayAdapter.roleAdapter!!)
//    val autoFillTextAdapter = PeopleAdapter(view.context, R.layout.content_profileedit, R.id.text_title, pArrayAdapter.roleAdapter!!)
//
//    view.setAdapter(autoFillTextAdapter)
//    var cityName: String
//    if (pArrayAdapter.cityName.isNullOrBlank()) {
//        cityName = ""
//    } else {
//        cityName = pArrayAdapter.cityName
//    }
//    view.setText(cityName)
//}
//
//@BindingAdapter("gender_array")
//fun setGenderArray(view: Spinner, pArrayAdapter: ProfileEditViewModel) {
//    val spinnerAdapter = SpinnerAdapter(view.context, R.layout.spinneritem,
//            pArrayAdapter.singleAttribute!!.gender!!, EnumSingleAttribute.GENDER)
//    view.adapter = spinnerAdapter
//
//    view.onItemSelectedListener = object : OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            pArrayAdapter.itemPositionGender = position
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//}
//
//@BindingAdapter("ethnic_array")
//fun setEthnicArray(view: Spinner, pArrayAdapter: ProfileEditViewModel) {
//    val spinnerAdapter = SpinnerAdapter(view.context, R.layout.spinneritem,
//            pArrayAdapter.singleAttribute!!.ethnicity!!, EnumSingleAttribute.ETHINICITY)
//    view.adapter = spinnerAdapter
//
//    view.onItemSelectedListener = object : OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            pArrayAdapter.itemPositionEthnic = position
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//
//}
//
//@BindingAdapter("religion_array")
//fun setReligionArray(view: Spinner, pArrayAdapter: ProfileEditViewModel) {
//    val spinnerAdapter = SpinnerAdapter(view.context, R.layout.spinneritem,
//            pArrayAdapter.singleAttribute!!.religion!!, EnumSingleAttribute.RELIGION)
//    view.adapter = spinnerAdapter
//    view.onItemSelectedListener = object : OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            pArrayAdapter.itemPositionReligion = position
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//
//}
//
//@BindingAdapter("figure_array")
//fun setFigureArray(view: Spinner, pArrayAdapter: ProfileEditViewModel) {
//    val spinnerAdapter = SpinnerAdapter(view.context, R.layout.spinneritem,
//            pArrayAdapter.singleAttribute!!.figure!!, EnumSingleAttribute.FIGURE)
//    view.adapter = spinnerAdapter
//
//    view.onItemSelectedListener = object : OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            pArrayAdapter.itemPositionFigure = position
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//
//}
//
//@BindingAdapter("martial_array")
//fun setMartialArray(view: Spinner, pArrayAdapter: ProfileEditViewModel) {
//    val spinnerAdapter = SpinnerAdapter(view.context, R.layout.spinneritem,
//            pArrayAdapter.singleAttribute!!.maritalStatus!!, EnumSingleAttribute.MARTIALSTATUS)
//    view.adapter = spinnerAdapter
//
//    view.onItemSelectedListener = object : OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            pArrayAdapter.itemPositionMartialStatus = position
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//
//}
//
//@BindingAdapter("category_array")
//fun setCategory(view: Spinner, pArrayAdapter: PostAdViewModel) {
//    val spinnerAdapter = SpinnerAdapter(view.context, R.layout.spinneritem,
//            pArrayAdapter.singleAttribute!!.category, EnumSingleAttribute.CATEGORY)
//    view.adapter = spinnerAdapter
//    pArrayAdapter.itemPositionCategory = 0
//
//    view.onItemSelectedListener = object : OnItemSelectedListener {
//        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            pArrayAdapter.itemPositionCategory = position
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//
//        }
//    }
//}
//
//@BindingAdapter("myAdsRecyclerView")
//fun myAdsRecyclerViewAdapter(recyclerView: RecyclerView, profileInfoViewModel: MyAdsListViewModel) {
//    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
//    recyclerView.adapter = MyAdsLstItemRecyclerAdpater(profileInfoViewModel)
//}
//


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
    autoText.setText("")
    autoText.onItemClickListener = object : OnItemSelectedListener, AdapterView.OnItemClickListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Log.d("user", "slecgt" + autoFillTextAdapter.suggestions.get(position))
        }

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Toast.makeText(parent!!.context, "Spinner item 1! " + autoFillTextAdapter.suggestions.get(position), Toast.LENGTH_SHORT).show()
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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
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
        }

        override fun onItemRangeChanged(sender: ObservableList<CountriesInfoModel>?, positionStart: Int, itemCount: Int) {
            Log.d("rach", "rach4")
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });
}


@BindingAdapter( "app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: MyDiscussionModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = MyDiscussionAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreMyDiscussionHandler(countriesViewModel, listAdapter)


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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });
}



@BindingAdapter( "app:searchAdapter", "app:searchRecycler")
fun adapter(searchView: SearchView,countriesViewModel: DiscussionModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = DiscussionAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreDiscussionHandler(countriesViewModel, listAdapter)


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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String?): Boolean {
            countriesViewModel.doGetTalentsSearch(s!!)
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {

            return false
        }
    })
}

@BindingAdapter( "app:searchRecycler2")
fun adapter2(recyclerView: RecyclerView, countriesViewModel: AdSearchModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = EventsListAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreEventsHandler(countriesViewModel, listAdapter)

    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
    bindingAdapter.initRequest(recyclerView)

    countriesViewModel.eventsList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<CountriesInfoModel>>() {
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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });
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

    ss.setSpan(clickableSpan, 34, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.setMovementMethod(LinkMovementMethod.getInstance())
    textView.setText(ss)


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

@BindingAdapter("app:broken","app:position","app:viewModel")
fun loadImage(view: ImageView, imageUrl: DiscussionModel, position: Int,postDiscussion: PostDiscussion) {

    view.isSelected = imageUrl.isBookmarked(postDiscussion)!!
    postDiscussion.bookmarks.notNull {  }
    view.setOnClickListener({ it.isSelected = !it.isSelected })

}


//@BindingAdapter("app:broken","app:position","app:viewModel")
//fun loadImage(view: ImageView, imageUrl: GroupsModel, position: Int,postDiscussion: Groups) {
//
//    view.isSelected = imageUrl.isBookmarked(postDiscussion)!!
//    postDiscussion.members.notNull {  }
//    view.setOnClickListener({ it.isSelected = !it.isSelected })
//
//}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, profileInfoViewModel: GroupViewModel ) {

    val emptySting = "";
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    val adapter = GroupAdapter()
    recyclerView.adapter = adapter
    (recyclerView.adapter as GroupAdapter).setModel(profileInfoViewModel)
    // (recyclerView.adapter as RatingsAdapter).setData(profileInfoViewModel.userIds)

    profileInfoViewModel.adapter = recyclerView.adapter as GroupAdapter

}


    @BindingAdapter( "app:searchAdapter", "app:searchRecycler")
    fun adapter(searchView: SearchView,countriesViewModel: GroupsModel,recyclerView: RecyclerView) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = GroupsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreGroupsHandler(countriesViewModel, listAdapter)


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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String?): Boolean {
            countriesViewModel.doGetTalentsSearch(s!!)
            return false
        }

        override fun onQueryTextChange(strQuery: String): Boolean {

            return false
        }
    })

}

@BindingAdapter( "app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: MyGroupsModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = MyGroupsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreMyGroupsHandler(countriesViewModel, listAdapter)


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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });
}

@BindingAdapter( "app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: TheEventsModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = TheEventsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreTheEvventsHandler(countriesViewModel, listAdapter)


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
        }

        override fun onChanged(sender: ObservableList<CountriesInfoModel>?) {
            Log.d("rach", "rach5")
        }

    });
}