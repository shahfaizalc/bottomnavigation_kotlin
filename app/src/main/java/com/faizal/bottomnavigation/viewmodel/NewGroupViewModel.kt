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
import com.faizal.bottomnavigation.listeners.EmptyResultListener
import com.faizal.bottomnavigation.listeners.MultipleClickListener
import com.faizal.bottomnavigation.model2.Groups
import com.faizal.bottomnavigation.model2.PostDiscussion
import com.faizal.bottomnavigation.model2.PostEvents
import com.faizal.bottomnavigation.network.FirbaseWriteHandler
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.util.getUserName
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.view.FragmentGameChooser
import com.faizal.bottomnavigation.view.FragmentNewDiscusssion
import com.faizal.bottomnavigation.view.FragmentNewGroup
import com.faizal.bottomnavigation.view.FragmentSimiliarDiscussion
import com.google.firebase.auth.FirebaseAuth
import org.greenrobot.eventbus.EventBus


class NewGroupViewModel(private val context: Context, private val fragmentSignin: FragmentNewGroup) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false


    init {
        networkHandler()
    }


    @get:Bindable
    var userTitle: String? =  ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userTitle)
        }

    @get:Bindable
    var userDesc: String? =  ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.userDesc)
        }

    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if ( userTitle.isNullOrEmpty() || !userTitle!!.isValid() || userDesc.isNullOrEmpty() ) {
                Toast.makeText(context, context.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( userTitle!!.isValid() ) {

                val group = Groups();
                group.title= userTitle
                group.description = userDesc
                group.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                group.postedDate = System.currentTimeMillis().toString()
                group.postedByName = getUserName(context.applicationContext, FirebaseAuth.getInstance().currentUser?.uid!!).name!!

                Log.d(TAG, "DocumentSnapshot  doDiscussionWrrite "  )
                val firbaseWriteHandler = FirbaseWriteHandler(fragmentSignin).updateGroups(group, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                        Toast.makeText(fragmentSignin.context, fragmentSignin.context!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()

                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
//                        val fragment = FragmentProfile()
//                        val bundle = Bundle()
//                        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//                        fragment.setArguments(bundle)
//                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

                    }
                })
            }
        }
    }

    fun String.isValid()= this.length > 8


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
