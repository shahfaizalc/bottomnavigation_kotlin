package com.nioneer.nioneer.viewmodel


import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.nioneer.nioneer.BR
import com.nioneer.nioneer.Events.MyCustomEvent
import com.nioneer.nioneer.R
import com.nioneer.nioneer.listeners.EmptyResultListener
import com.nioneer.nioneer.model2.*
import com.nioneer.nioneer.network.FirbaseWriteHandler
import com.nioneer.nioneer.util.GenericValues
import com.nioneer.nioneer.util.MultipleClickHandler
import com.nioneer.nioneer.util.firestoreSettings
import com.nioneer.nioneer.util.notNull
import com.nioneer.nioneer.utils.Constants
import com.nioneer.nioneer.view.FirestoreChatFragmment
import com.nioneer.nioneer.view.FragmentGroups
import com.nioneer.nioneer.view.FragmentMyGroups
import com.nioneer.nioneer.view.FragmentNewGroup
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyGroupsModel(internal var activity: FragmentActivity,
                    internal val fragmentProfileInfo: FragmentMyGroups)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Groups>


    var query : Query
    var db :FirebaseFirestore
    val mAuth: FirebaseAuth

    companion object {

        private val TAG = "MyGroupsModel"
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
        query = db.collection("groups")
                .orderBy("postedDate", Query.Direction.DESCENDING).limit(10)
        doGetTalents()
    }


    @get:Bindable
    var finderTitle: String? = activity.resources.getString(R.string.finderEventTitle)
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


    fun openFragment2(postAdModel: Groups, position: Int) {
//        val fragment = FirestoreChatFragmment()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().groupToString(postAdModel))
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

        val intent = Intent(fragmentProfileInfo.activity, FirestoreChatFragmment::class.java);
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().groupToString(postAdModel))
        fragmentProfileInfo.activity!!.startActivity(intent)


    }

    fun getMember(postAdModel: Groups): Members {

        var bookmak = Members()
        val bookmarks: MutableIterator<Members> = postAdModel.members!!.iterator()
        while (bookmarks.hasNext()) {
            val name = bookmarks.next()
            if (name.memberId.equals(mAuth.currentUser?.uid)) {

                bookmak = name;
            }
        }
        return  bookmak;
    }

    fun leaveGroup(postAdModel: Groups, position: Int){

        postAdModel.joinedBy?.remove(mAuth.currentUser?.uid)
        postAdModel.members?.remove(getMember(postAdModel))

        FirbaseWriteHandler(fragmentProfileInfo).updateJoin(postAdModel, object : EmptyResultListener {
            override fun onFailure(e: Exception) {
                Log.d("TAG", "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                Toast.makeText(fragmentProfileInfo.context, fragmentProfileInfo.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Log.d("TAG", "DocumentSnapshot onSuccess updateLikes")

                Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
                val fragment = FragmentMyGroups()
                fragmentProfileInfo.mFragmentNavigation.replaceFragment(fragment);
            }
        })
    }

    private fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }

    @Override
    fun onNextButtonClick() = View.OnClickListener() {

//        val fragment = FragmentNewGroup()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

        val intent = Intent(fragmentProfileInfo.activity, FragmentNewGroup::class.java);
        fragmentProfileInfo.activity!!.startActivity(intent)

    }

    fun doGetTalents() {

       val db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }



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

    fun addTalentsItems(document: QueryDocumentSnapshot) {

        val adModel = document.toObject(Groups::class.java)

        Log.d(TAG, "Success getting documents:groups " + adModel.postedBy)

        if (adModel.postedBy.equals(mAuth.currentUser!!.uid) ) {
            talentProfilesList.add(adModel)
        } else {

            if(getKeyWords(adModel.members)){
                Log.d(TAG, "Success getting documents:groups add" )

                getKeyWords2(talentProfilesList,adModel)

                if(!isUpdated) {
                    talentProfilesList.add(adModel)
                }

            } else {
                Log.d(TAG, "Success getting documents:groups A remove" +talentProfilesList.size)
                talentProfilesList = getKeyWords2(talentProfilesList,adModel)
                Log.d(TAG, "Success getting documents:groups B remove" +talentProfilesList.size)

            }
        }
    }

    private fun getKeyWords(keyWords: ArrayList<Members>?): Boolean {

        var result = false

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    value.memberId.notNull {
                        if (value.memberId.equals(mAuth.currentUser!!.uid)) {
                            result = true
                            return@notNull
                        }
                    }
                }
            }
        }
        return result;
    }

    var isUpdated = false

    private fun getKeyWords2(keyWords: ObservableArrayList<Groups>, keyWord: Groups): ObservableArrayList<Groups> {

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


    @Override
    fun doFindGroups() = View.OnClickListener() {

//        val fragment = FragmentGroups()
//        val bundle = Bundle()
//        fragment.setArguments(bundle)
//        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));
//
        val intent = Intent(fragmentProfileInfo.activity, FragmentGroups::class.java);
        fragmentProfileInfo.activity!!.startActivity(intent)


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
