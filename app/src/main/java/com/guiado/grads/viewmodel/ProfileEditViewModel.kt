package com.guiado.grads.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guiado.grads.model.IndiaItem

import com.guiado.grads.BR
import com.guiado.grads.Events.MyCustomEvent
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.EmptyResultListener
import com.guiado.grads.listeners.MultipleClickListener
import com.guiado.grads.model.CoachItem
import com.guiado.grads.model2.Profile
import com.guiado.grads.network.FirbaseWriteHandler
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.utils.Constants
import com.guiado.grads.view.FragmentAddress
import com.guiado.grads.view.FragmentKeyWords
import com.guiado.grads.view.FragmentProfile
import com.guiado.grads.view.FragmentProfileEdit
import com.google.firebase.auth.FirebaseAuth
import com.guiado.grads.adapter.CountryAdapter2
import com.guiado.grads.network.FirbaseWriteHandlerActivity
import com.guiado.grads.util.storeUserName
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList


class ProfileEditViewModel(private val context: Context, private val fragmentSignin: FragmentProfileEdit, postAdObj: String) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    var profile = Profile();
    val dialog = Dialog(context)
    var  observableArrayList =  ArrayList<IndiaItem>()
    var  observableArrayListFilter =  ArrayList<IndiaItem>()

    init {
        networkHandler()
        profile = (GenericValues().getProfile(postAdObj, fragmentSignin))
        readAutoFillItems()
        observableArrayList = readAutoFillItems2()

    }

    private fun readAutoFillItems() {
        val c = GenericValues()
        listOfCoachings = c.readCourseCategory(context)

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
    /*
   Method will act as the event handler for MyCustomEvent.kt
   */
    @Subscribe
    fun customEventReceived(event: MyCustomEvent) {
        EventBus.getDefault().unregister(this)
        profile = event.data
      //  userAddress = getAddress()
        keys = getKeyWords()
    }

    private fun getAddress() = " " + profile.address?.locationname + "\n " + profile.address?.streetName +
            ", " + profile.address?.town + "\n " + profile.address?.city

    private fun getLocation() = profile.location

    private fun getKeyWords(): String {

        var result = ""

        val numbersIterator = profile.keyWords?.iterator()
        numbersIterator?.let {
            while (numbersIterator.hasNext()) {
                var value = (numbersIterator.next())
                result += "" + listOfCoachings!!.get(value - 1).categoryname +", "
            }
        }

        return result;
    }


    @get:Bindable
    var listOfCoachings: ArrayList<CoachItem>? = null
        private set(roleAdapterAddress) {
            field = roleAdapterAddress
            notifyPropertyChanged(BR.roleAdapterAddress)
        }


    var imgUrl = ""


    @get:Bindable
    var userEmail: String? = FirebaseAuth.getInstance().currentUser?.email
        set(price) {
            field = price
            profile.email = price
            notifyPropertyChanged(BR.userEmail)

        }


//    @get:Bindable
//    var userAddress: String? = getAddress()
//        set(price) {
//            field = price
//            notifyPropertyChanged(BR.userAddress)
//
//        }

    @get:Bindable
    var keys: String? = getKeyWords()
        set(price) {
            field = price
            notifyPropertyChanged(BR.keys)

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
    fun datePickerClick() = View.OnClickListener {


        if (!handleMultipleClicks()) {

            if (profile.name != "" && profile.email != "" && profile.phone != "" && profile.title != "") {
                showProgresss(true)
                val firbaseWriteHandler = FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(profile, object : EmptyResultListener {
                    override fun onFailure(e: Exception) {
                        Log.d(TAG, "DocumentSnapshot onFailure " + e.message)
                        Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.errorMsgGeneric), Toast.LENGTH_SHORT).show()
                        showProgresss(false)

                    }

                    override fun onSuccess() {
                        showProgresss(false)
                        Log.d(TAG, "DocumentSnapshot onSuccess ")
                        storeUserName(context, FirebaseAuth.getInstance().currentUser!!.uid, profile)

//                        val fragment = FragmentProfile()
//                        val bundle = Bundle()
//                        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//                        fragment.setArguments(bundle)
                        fragmentSignin.finish()

                    }
                })
            } else {
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.loginValidtionErrorMsg), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun registerEventBus(){
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }
    fun updateAddress() = View.OnClickListener {
        registerEventBus();
//        val fragment = FragmentAddress()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));

        val intent = Intent(fragmentSignin,FragmentAddress::class.java);
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragmentSignin.startActivity(intent)
    }

    fun updateKeyWords() = View.OnClickListener {
        registerEventBus();
//        val fragment = FragmentKeyWords()
//        val bundle = Bundle()
//        bundle.putString(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
//        fragment.setArguments(bundle)
//        fragmentSignin.mFragmentNavigation.pushFragment(fragmentSignin.newInstance(1, fragment, bundle));


        val intent = Intent(fragmentSignin,FragmentKeyWords::class.java);
        intent.putExtra(Constants.POSTAD_OBJECT, GenericValues().profileToString(profile))
        fragmentSignin.startActivity(intent)
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

    fun filterByCategory(position: Int) {
        dialog.dismiss()
        userLocation =  observableArrayListFilter.get(position).cityname
    }


    @get:Bindable
    var userLocation: String? = getLocation()
        set(price) {
            field = price
            notifyPropertyChanged(BR.userLocation)

            profile.location = field
        }
    private fun readAutoFillItems2() : ArrayList<IndiaItem> {
        val values = GenericValues()
        return values.readAutoFillItems(context)
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
            val customAdapter = CountryAdapter2(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)
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
}
