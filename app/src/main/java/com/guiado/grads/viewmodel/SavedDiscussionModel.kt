package com.guiado.grads.viewmodel


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.guiado.grads.BR
import com.guiado.grads.Events.MyCustomEvent
import com.guiado.grads.R
import com.guiado.grads.model.EventStatus
import com.guiado.grads.model2.PostDiscussion
import com.guiado.grads.model2.Profile
import com.guiado.grads.util.*
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.FirestoreMyDisccussFragmment
import com.guiado.grads.view.FragmentNewDiscusssion
import com.guiado.grads.view.FragmentSavedDiscussions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SavedDiscussionModel( internal val fragmentProfileInfo: FragmentSavedDiscussions)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<PostDiscussion>

    var query : Query
    var db :FirebaseFirestore
    private val mAuth: FirebaseAuth

    companion object {

        private val TAG = "AdSearchModel"
    }


    init {
        talentProfilesList = ObservableArrayList()
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }
        query = db.collection("discussion")
                .orderBy("postedDate", Query.Direction.DESCENDING).limit(10)
                .whereArrayContains("bookmarkBy",mAuth.currentUser!!.uid)
        doGetTalents()
    }



    @get:Bindable
    var finderTitle: String? = fragmentProfileInfo.resources.getString(R.string.finderEventTitle)
        set(city) {
            field = city
            notifyPropertyChanged(BR.finderTitle)
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


    fun openFragment2(postAdModel: PostDiscussion, position: Int) {
        if (postAdModel.eventState.ordinal < EventStatus.HIDDEN.ordinal) {

//            val fragment = FirestoreMyDisccussFragmment()
//            val bundle = Bundle()
//            bundle.putString(Constants.POSTAD_OBJECT, GenericValues().discussionToString(postAdModel))
//            fragment.setArguments(bundle)
//            fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));
//
            val intent = Intent(fragmentProfileInfo, FirestoreMyDisccussFragmment::class.java);
            intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().discussionToString(postAdModel))
            fragmentProfileInfo.startActivity(intent)

        } else {
            showPopUpWindow();
        }
    }

    fun showPopUpWindow() {
        val view = getNotificationContentView(fragmentProfileInfo,
                fragmentProfileInfo.applicationContext.resources.getString(R.string.oops_title),
                fragmentProfileInfo.applicationContext.resources.getString(R.string.oops_msg))
        val popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        view.findViewById<View>(R.id.closeBtn).setOnClickListener {
            popupWindow.dismiss()

        }
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {

//        val fragment = FragmentNewDiscusssion()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

        val intent = Intent(fragmentProfileInfo, FragmentNewDiscusssion::class.java)
        fragmentProfileInfo.startActivity(intent)

    }


    private fun getCommbinationWords(s: String): List<String> {
        val list1 = s.sentenceToWords()
        Log.d("list2", "indian" + list1)
        return list1
    }

    fun doGetTalentsSearch(searchQuery: String) {
        query = db.collection("discussion")
                .whereArrayContainsAny("searchTags", getCommbinationWords(searchQuery).toList())
                .orderBy("postedDate", Query.Direction.DESCENDING)
                .limit(10).whereArrayContains("bookmarkBy",mAuth.currentUser!!.uid)

        Log.d(TAG, "DOIT doGetTalentsSearch: ")
        talentProfilesList.removeAll(talentProfilesList)
        doGetTalents()

    }


    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(PostDiscussion::class.java)

        Log.d(TAG, "Success getting documents: " + adModel.postedBy)

        getKeyWords(talentProfilesList,adModel)

        if(!isUpdated) {
            talentProfilesList.add(adModel)
        }

    }

    var isUpdated = false

    private fun getKeyWords(keyWords: ObservableArrayList<PostDiscussion>,keyWord: PostDiscussion): ObservableArrayList<PostDiscussion> {

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
            query = query.startAfter(lastVisible)

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

}
