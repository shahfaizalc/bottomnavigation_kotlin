package com.reelme.app.viewmodel

import android.R.string
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.model3.UserDetails
import com.reelme.app.view.*
import java.text.SimpleDateFormat
import java.util.*


class DateViewModel(private val context: Context, private val fragmentSignin: FragmentDate) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
       // fragmentSignin.finish()

        if(!firstName.isNullOrEmpty()){
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentUserName::class.java));

        }
    }

    lateinit var dateOfBirth : Date

    fun setDateClicked() {
        // fragmentSignin.finish()
        val myCalendar: Calendar = Calendar.getInstance()

        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "MM / dd / yyyy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)

           if(myCalendar.timeInMillis <= System.currentTimeMillis()){

               val date = sdf.parse(sdf.format(myCalendar.time))
               userDetails.dob = date

               firstName = (sdf.format(myCalendar.time))
           }else{
               showToast(R.string.invalid_date_ErrorMsg)
           }
        }

         // TODO Auto-generated method stub
        DatePickerDialog(fragmentSignin, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()

    }

    lateinit var userDetails : UserDetails

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserDetails::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserDetails)
            signInUserClicked()
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    fun setUserInfo(){

        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.apply()

    }

    @get:Bindable
    var firstName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }
    fun signUpUserClicked() {
       // fragmentSignin.finish()

        if(!firstName.isNullOrEmpty()){
            setUserInfo()
            fragmentSignin.startActivity(Intent(fragmentSignin, FragmentUserName::class.java));

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
}
