package com.faizal.bottomnavigation.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.handler.NetworkChangeHandler
import com.faizal.bottomnavigation.listeners.MultipleClickListener
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.model2.PostEvents
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentGameChooser
import com.faizal.bottomnavigation.view.FragmentNewDiscusssion
import com.faizal.bottomnavigation.view.FragmentSimiliarDiscussion
import com.google.firebase.auth.FirebaseAuth
import org.greenrobot.eventbus.EventBus


class NewDiscussionViewModel(private val context: Context, private val fragmentSignin: FragmentNewDiscusssion) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var postEvents = PostDiscussion();

    init {
        networkHandler()
        postEvents = PostDiscussion();
    }


    @get:Bindable
    var userTitle: String? = postEvents.title ?: ""
        set(price) {
            field = price
            postEvents.title = field
            notifyPropertyChanged(BR.userTitle)


        }


    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
//            if (postEvents.title.isNullOrEmpty()) {
//                Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.infoMsg), Toast.LENGTH_LONG).show()
//                return@OnClickListener
//            }

            postEvents.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            postEvents.postedDate = System.currentTimeMillis().toString()
            Log.d(TAG, "DocumentSnapshot onFailure i am in ")

            Log.d(TAG, "DocumentSnapshot onSuccess ")
            val fragment = FragmentGameChooser()
            val bundle = Bundle()
            bundle.putString(Constants.POSTAD_OBJECT, GenericValues().discussionToString(postEvents))

            fragment.setArguments(bundle)
            fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));
        }

    }


    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(context)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(context)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }

    override fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}
