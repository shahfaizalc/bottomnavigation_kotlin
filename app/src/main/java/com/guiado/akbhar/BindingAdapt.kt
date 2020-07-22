package com.guiado.akbhar

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.adapter.*
import com.guiado.akbhar.handler.*
import com.guiado.akbhar.model.CountriesInfoModel
import com.guiado.akbhar.model.Feed
import com.guiado.akbhar.util.getMagazines
import com.guiado.akbhar.util.getNewsProviders
import com.guiado.akbhar.viewmodel.*
import com.squareup.picasso.Picasso

@BindingAdapter("app:recyclerChannelComedy")
fun adapter(recyclerView: RecyclerView, channelsViewModel: IntroViewModel) {

    val dataModelVal = channelsViewModel.channelTamilMovieReviewDataModel;
    //val genericValues = GenericValues();

    val recyclerItems = getNewsProviders()

    Log.d("iteems", "iteems " + recyclerItems)

    val linearLayoutManager = GridLayoutManager(recyclerView.context, 3, GridLayoutManager.VERTICAL, false)
    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
    val listAdapter = IntroChannelsRecyclerViewAdapter(channelsViewModel, dataModelVal)
    recyclerView.adapter = listAdapter
    val bindingAdapter = RecyclerLoadMoreIntroChannelsComedyHandler(channelsViewModel, listAdapter, dataModelVal)
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
    bindingAdapter.initRequest(recyclerView, recyclerItems, false)

}

@BindingAdapter("app:recyclerChannelComedy")
fun adapter(recyclerView: RecyclerView, channelsViewModel: MagazineViewModel) {

    val dataModelVal = channelsViewModel.channelTamilMovieReviewDataModel;
    //val genericValues = GenericValues();

    val recyclerItems = getMagazines()

    Log.d("iteems", "iteems " + recyclerItems)

    val linearLayoutManager = GridLayoutManager(recyclerView.context, 1, GridLayoutManager.VERTICAL, false)
    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
    val listAdapter = MagazineRecyclerViewAdapter(channelsViewModel, dataModelVal)
    recyclerView.adapter = listAdapter
    val bindingAdapter = RecyclerLoadMoreMagazineHandler(channelsViewModel, listAdapter, dataModelVal)
    bindingAdapter.scrollListener(recyclerView, linearLayoutManager)
    bindingAdapter.initRequest(recyclerView, recyclerItems, false)

}

@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: CoronaModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = CoronaAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreCoronaHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: DiscussionModel) {

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
            if (countriesViewModel.resetScrrollListener) {
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
}



@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: ArtViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = ArtAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreArtHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: FoodViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = FoodAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreFoodHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}



@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: GameViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = GameAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreGameHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: BusinessViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = BusinessAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreBusinessHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: EntertainementViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = EntertainmentAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreEntertainmentHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}



@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: MoroccoViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = MoroccoAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreMoroccoHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: PoliticsViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = PoliticsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMorePoliticsHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}



@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: ScienceViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = ScienceAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreScienceHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: SportsViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = SportsAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreSportsHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}

@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: TechViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = TechnologyAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreTechnologyHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}


@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: TravelViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = TravelAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreTravelHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}

@BindingAdapter("app:searchRecycler")
fun adapter(recyclerView: RecyclerView, countriesViewModel: WorldViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = WorldAdapter(countriesViewModel)
    val bindingAdapter = RecyclerLoadMoreWorldHandler(countriesViewModel, listAdapter)
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
            if (countriesViewModel.resetScrrollListener) {
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
}



@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    val i = TextUtils.isEmpty(imageUrl)


    if (i) {
        view.setImageDrawable(view.getContext().getDrawable(R.drawable.placeholder_profile))
    } else {
        Picasso.get()
                .load(imageUrl)
                .error(R.drawable.placeholder_profile)
                .placeholder(R.drawable.placeholder_profile)
                .into(view)
    }
}



/**
 * To set webview client
 * @param webView : Article web view
 * @param client : web view client
 */
@BindingAdapter("setWebViewClient")
fun setWebViewClient(webView: WebView, client: WebViewClient) {
    webView.webViewClient = client
    WebView.setWebContentsDebuggingEnabled(false)
    webView.settings.javaScriptEnabled = true
    webView.settings.loadsImagesAutomatically
}

/**
 * To load the webview
 * @param webView : Article web view
 * @param webViewModel: webview viewmodel class
 */
@BindingAdapter("loadUrl")
fun loadUrl(webView: WebView, webViewModel: WebViewModel) {
    with(webViewModel) {
        when (webViewUrl) {
            "" -> {
                msgView = View.VISIBLE
                msg = webView.context.resources.getString(R.string.rssblog_web_error)
                progressBarVisible = View.GONE
            }
            else -> webView.loadUrl(webViewUrl)
        }
    }
}



