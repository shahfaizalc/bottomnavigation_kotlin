package com.guiado.akbhar.viewmodel


import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.akbhar.BR
import com.guiado.akbhar.Events.MyCustomEvent
import com.guiado.akbhar.R
import com.guiado.akbhar.model2.*
import com.guiado.akbhar.util.*
import com.guiado.akbhar.utils.Constants
import com.guiado.akbhar.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.guiado.akbhar.adapter.CountryAdapter
import com.guiado.akbhar.model.EventStatus
import com.guiado.akbhar.model.IndiaItem
import com.guiado.akbhar.model.SearchMode
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

class TheEventsModel(internal var activity: FragmentActivity,
                     internal val fragmentProfileInfo: FragmentTheEvents)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Events>
    var query : Query
    var db :FirebaseFirestore
    val dialog = Dialog(activity)

    var resetScrrollListener : Boolean = false;

    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }
    var  observableArrayList =  ArrayList<IndiaItem>()
    var  observableArrayListFilter =  ArrayList<IndiaItem>()

    init {
        talentProfilesList = ObservableArrayList()
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        query = db.collection("events").orderBy("startDate", Query.Direction.ASCENDING).limit(10).whereGreaterThanOrEqualTo("startDate",System.currentTimeMillis().toString())
        doGetTalents()
        observableArrayList = readAutoFillItems()


    }

    @get:Bindable
    var finderTitle: String? = activity.resources.getString(R.string.finderEventTitle)
        set(city) {
            field = city
            notifyPropertyChanged(BR.finderTitle)
        }


    @get:Bindable
    var showClearFilter: Int = View.GONE
        set(city) {
            field = city
            notifyPropertyChanged(BR.showClearFilter)
        }

    @get:Bindable
    var searchMode = SearchMode.DEFAULT
        set(city) {
            field = city

            if(searchMode.ordinal == SearchMode.DEFAULT.ordinal)
                showClearFilter = View.GONE
            else{
                showClearFilter = View.VISIBLE

            }
        }

    @Override
    fun onFilterClearClick() = View.OnClickListener() {
        showClearFilter = View.GONE
        searchMode = SearchMode.DEFAULT
        query = db.collection("events").orderBy("startDate", Query.Direction.ASCENDING).limit(10).whereGreaterThanOrEqualTo("startDate",System.currentTimeMillis().toString())
        resetScrrollListener = true
        talentProfilesList.removeAll(talentProfilesList)

        doGetTalents()
    }

    private fun readAutoFillItems() : ArrayList<IndiaItem> {
        val values = GenericValues()
        return values.readAutoFillItems(activity.applicationContext)
    }



    @Override
    fun onFilterClick() = View.OnClickListener() {

        if(!handleMultipleClicks()) {

            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_listview2)

            val btndialog: TextView = dialog.findViewById(R.id.btndialog) as TextView
            btndialog.setOnClickListener({ dialog.dismiss() })

            observableArrayListFilter = observableArrayList

            val recyclerView = dialog.findViewById(R.id.listview) as RecyclerView
            val customAdapter = CountryAdapter(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = customAdapter


            var searchView = dialog.findViewById<SearchView>(R.id.search1)
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG," query "+query)

                    val model =
                            observableArrayList.filter {
                                it.cityname?.toLowerCase()?.contains(query!!.toLowerCase())!!
                            }
                    val arrayList = ObservableArrayList<IndiaItem>()
                    arrayList.addAll(model)
                    observableArrayListFilter = arrayList
                    Log.d(TAG," query "+observableArrayListFilter.size)
                    recyclerView.post { customAdapter.notifyItemChanged(0,0)}
                    recyclerView.post { customAdapter.notifyDataSetChanged() }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.length == 0) {
                        observableArrayListFilter = observableArrayList
                        Log.d(TAG, " query " + observableArrayListFilter.size)
                        recyclerView.post { customAdapter.notifyItemChanged(0, 0) }
                        recyclerView.post { customAdapter.notifyDataSetChanged() }
                    }

                    return false
                }
            })

//            listView.setOnClickListener()
//
//            listView.setOnItemClickListener({ parent, view, position, id ->
//
//                dialog.dismiss()
//
//                filterByCategory(position)
//            })

            dialog.show()
        }
    }





    fun filterByCategory(position: Int) {

        Toast.makeText(activity,"temmpa "+observableArrayListFilter.get(position).cityname,Toast.LENGTH_LONG).show()


        query = db.collection("events")
                .whereEqualTo("address.city", observableArrayListFilter.get(position).cityname)
                .orderBy("postedDate", Query.Direction.DESCENDING)
                .limit(10)

        dialog.dismiss()

        talentProfilesList.removeAll(talentProfilesList)

        if(searchMode.ordinal == SearchMode.DEFAULT.ordinal)
            searchMode = SearchMode.CATEGORY
        else{
            searchMode = SearchMode.CATEGORYANDSEARCH

        }
        doGetTalents()
    }

    /*
      Method will act as the event handler for MyCustomEvent.kt
      */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
    }
    var profile = Profile();


    fun openFragment2(postAdModel: Events, position: Int) {
//        val fragment = FragmentEvent()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));
//
//
        val intent = Intent(activity,FragmentEvent::class.java);
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().eventToString(postAdModel))
        activity.startActivity(intent);
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }


    private fun getCommbinationWords(s: String): List<String> {
        val list1 = s.sentenceToWords()
        Log.d("list2", "indian" + list1)
        return list1
    }

    fun doGetTalentsSearch(searchQuery: String) {
        query = db.collection("events")
                .whereArrayContainsAny("searchTags", getCommbinationWords(searchQuery).toList())
                .orderBy("postedDate", Query.Direction.DESCENDING)
                .limit(10)

        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }






    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Events::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.postedBy)

        if (!adModel.postedBy.equals(mAuth.currentUser!!.uid) && adModel.eventState.ordinal ==  EventStatus.SHOWING.ordinal) {

            getKeyWords(talentProfilesList,adModel)

            if(!isUpdated) {
                talentProfilesList.add(adModel)
            }

        }
    }


    var isUpdated = false


    private fun getKeyWords(keyWords: ObservableArrayList<Events>,keyWord: Events): ObservableArrayList<Events> {
        isUpdated = false
        var count = 0;


        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    if (value.postedDate.equals(keyWord.postedDate)){
                        isUpdated = true
                        talentProfilesList.set(count,keyWord)
                        return@notNull
                    }
                    count = count + 1;
                }
            }
        }
        return keyWords;
    }

    fun doGetTalents() {

        Log.d(TAG, "DOIT doGetTalents: ")

        // talentProfilesList.clear()
        query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen error", e)
                return@addSnapshotListener
            }
            Log.d(TAG, "DOIT doGetTalents: "+querySnapshot?.size())


            if (querySnapshot == null) {
                Log.i(TAG, "Listen querySnapshot end")
                return@addSnapshotListener
            }

            if (querySnapshot.size() < 1) {
                Log.i(TAG, "Listen querySnapshot end")
                return@addSnapshotListener
            }

            Log.d(TAG, "Listen querySnapshot end"+querySnapshot.size())

            val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
            query = query.limit(10).startAfter(lastVisible)

            for (change in querySnapshot.documentChanges) {
                if (change.type == DocumentChange.Type.ADDED) {
                    Log.d(TAG, "New city: ${change.document.data}")
                }

                val source = if (querySnapshot.metadata.isFromCache) {
                    "local cache"
                } else{
                    "server"
                }
                Log.d(TAG, "Data fetched from $source")
                addTalentsItems(change.document)


            }
        }
    }



    @Override
    fun onNextButtonClick() = View.OnClickListener() {

//        val fragment = FragmentNewEvent()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));
//
//        activity

        val intent = Intent(activity,FragmentNewEvent::class.java);
        activity.startActivity(intent);


    }

//    @get:Bindable
//    var membersCount: Int? = isBookmarked()
//        set(city) {
//            field = city
//            notifyPropertyChanged(BR.membersCount)
//        }

//    fun isBookmarked(postDiscussion: Groups): Int? {
//        var isFollow = false
//        postDiscussion.members.notNull {
//            val likes: MutableIterator<Bookmarks> = it.iterator()
//            while (likes.hasNext()) {
//                val name = likes.next()
//                if (name.markedById.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
//                    isFollow = true
//                }
//            }
//        }
//
//        return isFollow
//    }
}
