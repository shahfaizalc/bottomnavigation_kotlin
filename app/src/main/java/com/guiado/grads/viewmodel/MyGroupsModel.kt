package com.guiado.grads.viewmodel


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.guiado.grads.BR
import com.guiado.grads.Events.MyCustomEvent
import com.guiado.grads.R
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.model2.*
import com.guiado.grads.network.FirbaseWriteHandler
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.util.firestoreSettings
import com.guiado.grads.util.notNull
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.FirestoreChatFragmment
import com.guiado.grads.view.FragmentGroups
import com.guiado.grads.view.FragmentMyGroups
import com.guiado.grads.view.FragmentNewGroup
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyGroupsModel(internal var activity: FragmentActivity,
                    internal val fragmentProfileInfo: FragmentMyGroups)// To show list of user images (Gallery)
    : BaseObservable() {

    var talentProfilesList: ObservableArrayList<Groups>


    val mAuth: FirebaseAuth

    companion object {

        private val TAG = "MyGroupsModel"
    }


    init {
        talentProfilesList = ObservableArrayList()
        mAuth = FirebaseAuth.getInstance()
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
        val fragment = FirestoreChatFragmment()
        val bundle = Bundle()
        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().groupToString(postAdModel))
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1, fragment, bundle));

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

        val fragment = FragmentNewGroup()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

    }

    fun doGetTalents() {

       val db = FirebaseFirestore.getInstance()
        try {
            db.firestoreSettings = firestoreSettings
        } catch (e:Exception){
            Log.d(TAG, "getProfile  "+e)
        }

        val query = db.collection("groups");

        query.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen error", e)
                return@addSnapshotListener
            }
            for (change in querySnapshot!!.documentChanges) {
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
                talentProfilesList = getKeyWords2(talentProfilesList,adModel)
                talentProfilesList.add(adModel)
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

    private fun getKeyWords2(keyWords: ObservableArrayList<Groups>, keyWord: Groups): ObservableArrayList<Groups> {

        keyWords.notNull {
            val numbersIterator = it.iterator()
            numbersIterator.let {
                while (numbersIterator.hasNext()) {
                    val value = (numbersIterator.next())
                    if (value.postedDate.equals(keyWord.postedDate)){
                        keyWords.remove(value)
                        return@notNull
                    }
                }
            }
        }
        return keyWords;
    }


    @Override
    fun doFindGroups() = View.OnClickListener() {

        val fragment = FragmentGroups()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));

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
