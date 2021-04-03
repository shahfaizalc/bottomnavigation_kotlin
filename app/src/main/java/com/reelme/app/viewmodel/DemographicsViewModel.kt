package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class DemographicsViewModel(private val context: Context, private val fragmentSignin: FragmentDemographics) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {

    //    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));

        // fragmentSignin.finish()

//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }
    }


    fun signUpUserClicked() {
      //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }

    }

    @get:Bindable
    var genderTitle: String? = userDetails.gender
        set(price) {
            field = price
            notifyPropertyChanged(BR.genderTitle)
        }

    fun genderClicked() {
        //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }
        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, GenderActivity::class.java),2);

    }

    @get:Bindable
    var relationshipTitle: String? = userDetails.relationshipStatus
        set(price) {
            field = price
            notifyPropertyChanged(BR.relationshipTitle)
        }
    fun relationshipClicked() {
        //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }
        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, RelationshipActivity::class.java),2);

    }

    @get:Bindable
    var childrenTitle: String? = userDetails.children
        set(price) {
            field = price
            notifyPropertyChanged(BR.childrenTitle)
        }
    fun childrenClicked() {
        //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }
        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, ChildrenActivity::class.java),2);

    }

    @get:Bindable
    var occupationTitle: String? = userDetails.occupation
        set(price) {
            field = price
            notifyPropertyChanged(BR.occupationTitle)
        }
    fun occupationClicked() {
        //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }
        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentOccupation::class.java),2);

    }

    @get:Bindable
    var religiousTitle: String? = userDetails.religiousBeliefs
        set(price) {
            field = price
            notifyPropertyChanged(BR.religiousTitle)
        }
    fun religiousClicked() {
        //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }
        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, RelegionActivity::class.java),2);

    }



    @get:Bindable
    var hobbiesTitle: String? = userDetails.hobbiesAndInterest
        set(price) {

            field = price
            notifyPropertyChanged(BR.hobbiesTitle)
        }

    fun hobbiesClicked() {
        //  fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }
        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentHobbies::class.java),2);

    }


    private fun setEditShare(){

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("IS_EDIT",true)
        editor.apply()

    }

    private fun isValidName(name : String): Boolean {

        val regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"

        val pattern: Pattern = Pattern.compile(regex)

        val matcher: Matcher = pattern.matcher(name)
        println(name + " : " + matcher.matches())

        return if(matcher.matches()){
            true;
        } else{
            showToast(R.string.invalid_name_ErrorMsg)
            false
        }
    }

    lateinit var userDetails : UserModel
    private var isEdit = false;

     fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT",false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)

           childrenTitle =   userDetails.children
           relationshipTitle =  userDetails.relationshipStatus
           religiousTitle =   userDetails.religiousBeliefs
           genderTitle = userDetails.gender
           occupationTitle =  userDetails.occupation
           hobbiesTitle = userDetails.hobbiesAndInterest


        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    fun setUserInfo(){
        progressBarVisible = View.VISIBLE

        userDetails.firstName = firstName
        userDetails.secondName = lastName

        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.putBoolean("IS_EDIT",false)
        editor.apply()

        Toast.makeText(context, "Please wait... we are saving your data", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }


        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE
                if(isEdit){
                    fragmentSignin.setResult(2, Intent())
                    fragmentSignin.finish()
                } else{
                    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDate::class.java));
                }

                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
             //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception"+e)
                Toast.makeText(context, "Failed to save your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
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

    @get:Bindable
    var firstName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.lastName)
        }


    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

}
