package com.guiado.akbhar.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.guiado.akbhar.BR
import com.guiado.akbhar.R
import com.guiado.akbhar.handler.NetworkChangeHandler
import com.guiado.akbhar.listeners.EmptyResultListener
import com.guiado.akbhar.listeners.MultipleClickListener
import com.guiado.akbhar.model2.Groups
import com.guiado.akbhar.util.*
import com.guiado.akbhar.view.*
import com.google.firebase.auth.FirebaseAuth
import com.guiado.akbhar.network.FirbaseWriteHandlerActivity


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


    @get:Bindable
    var showSignUpProgress: Int = View.INVISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpProgress)
        }

    @get:Bindable
    var showSignUpBtn: Int = View.VISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpBtn)
        }

    fun showProgresss(isShow : Boolean){
        if(isShow){
            showSignUpBtn = View.INVISIBLE
            showSignUpProgress = View.VISIBLE }
        else{
            showSignUpBtn = View.VISIBLE
            showSignUpProgress = View.INVISIBLE
        }
    }

    private fun compareLIt(): List<String>  { return userTitle!!.sentenceToWords() }


    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if ( userTitle.isNullOrEmpty() || !userTitle!!.isValid() || userDesc.isNullOrEmpty() ) {
                Toast.makeText(context, context.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if ( userTitle!!.isValid() ) {
                showProgresss(true)

                val group = Groups();
                group.searchTags = compareLIt()
                group.title= userTitle
                group.description = userDesc
                group.postedBy = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                group.postedDate = System.currentTimeMillis().toString()
                group.postedByName = getUserName(context.applicationContext, FirebaseAuth.getInstance().currentUser?.uid!!).name!!

                Log.d(TAG, "DocumentSnapshot  doDiscussionWrrite "  )
                val firbaseWriteHandler = FirbaseWriteHandlerActivity(fragmentSignin).updateGroups(group, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot doDiscussionWrrite onFailure " + e.message)
                        Toast.makeText(fragmentSignin, fragmentSignin!!.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                        showProgresss(false)

                    }

                    override fun onSuccess() {
                        Log.d(TAG, "DocumentSnapshot onSuccess doDiscussionWrrite")
//                        val fragment = FragmentMyGroups()
//                        val bundle = Bundle()
//                        fragment.setArguments(bundle)
//                        fragmentSignin.mFragmentNavigation.replaceFragment(fragment);
                        showProgresss(false)
                        fragmentSignin.finish()

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
