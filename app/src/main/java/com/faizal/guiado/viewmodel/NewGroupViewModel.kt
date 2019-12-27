package com.faizal.guiado.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.faizal.guiado.BR
import com.faizal.guiado.R
import com.faizal.guiado.handler.NetworkChangeHandler
import com.faizal.guiado.listeners.EmptyResultListener
import com.faizal.guiado.listeners.MultipleClickListener
import com.faizal.guiado.model2.Groups
import com.faizal.guiado.network.FirbaseWriteHandler
import com.faizal.guiado.util.*
import com.faizal.guiado.view.*
import com.google.firebase.auth.FirebaseAuth


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

    private fun compareLIt(): List<String>  { return userTitle!!.sentenceToWords() }


    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if ( userTitle.isNullOrEmpty() || !userTitle!!.isValid() || userDesc.isNullOrEmpty() ) {
                Toast.makeText(context, context.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( userTitle!!.isValid() ) {

                val group = Groups();
                group.searchTags = compareLIt()
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
                        val fragment = FragmentMyGroups()
                        val bundle = Bundle()
                        fragment.setArguments(bundle)
                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);

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
