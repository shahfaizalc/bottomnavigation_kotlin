package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.listeners.StringResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.EnumValidator
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.utils.Validator
import com.reelme.app.view.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class EditProfileViewModel(private val context: Context, private val fragmentSignin: FragmentEditProfile) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false

    private val progresPercentage = MutableLiveData<Int>()

    fun getProgressValue(): LiveData<Int?> {
        return progresPercentage
    }

    init {
        networkHandler()
        getUserInfo()
    }


    private fun setEditShare(){

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("IS_EDIT", true)
        editor.apply()

    }


    fun signInUserClicked() {
//
//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }

        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDemographics::class.java));

    }


    fun signEditSaveClicked() {

       if( Validator().validate(nameTitle, EnumValidator.NAME_PATTERN) && Validator().validate(usernameTitle, EnumValidator.USER_NAME_PATTERN)
               && Validator().validate(emailTitle, EnumValidator.EMAIL_PATTERN) ){
                   if(userDetails.username.equals( usernameTitle)){
                       setUserInfo()

                   } else{
                       FirbaseWriteHandlerActivity(fragmentSignin).doGetEvents(usernameTitle!!,object : StringResultListener {


                           override fun onSuccess(url: String) {
                               if(url.equals("0")){
                                   setUserInfo()
                                   // Toast.makeText(context, "Username available ", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                               } else{
                                   Toast.makeText(context, "Username already taken", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                               }
                           }

                           override fun onFailure(e: Exception) {
                               Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                           }
                       })
                   }


         //  setUserInfo()
       } else{
           Toast.makeText(context, "Kindly check email, username and name", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

       }

       // setEditShare()
      //  fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentUploadView::class.java),2);

    }


    fun signChangePhotoClicked() {
//
//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }

        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentUploadView::class.java), 2);

    }


    fun signInUserBioClicked() {
//
//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }

        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentBioMobile::class.java), 2);

    }

    fun signInUserEmailClicked() {
//
//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }

        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentEmailAddress::class.java), 2);

    }

    fun signInUserNameClicked() {
//
//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }

        setEditShare()
        fragmentSignin.startActivityForResult(Intent(fragmentSignin, FragmentUserName::class.java), 2);

    }


    fun signUpUserClicked() {
       // if( isValidName(firstName!!) && isValidName(lastName!!)){
        fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDeleteAccount::class.java));
       // }
    }



    private fun isValidName(name: String): Boolean {

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
    var percentage =""

    fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId!!)
            userDetails = (auth as UserModel)
            percentage = Validator().profileRate(userDetails).toString()
            percentof = " $percentage"
            Log.d("Authenticaiton token", "2percentage$percentage")
            photo = userDetails.profilePic
            //signInUserClicked()
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }



        Log.d("Authenticaiton token", "percentage$percentage")

    }

    fun setUserInfo(){
        progressBarVisible = View.VISIBLE




        val gsonValue = Gson().toJson(userDetails)

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()

        Toast.makeText(context, "Please wait... we are saving your data", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }


        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfoitems(mapOf(
                "firstName" to nameTitle,
                "username" to usernameTitle,
                "emailId" to emailTitle,
                "instagramLink" to instagramId,
                "youtube" to youtubeId,
                "countryOrigin" to countryid), object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE
                // if(isEdit){
                //   fragmentSignin.setResult(2, Intent())

                val sharedPreference = fragmentSignin.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putBoolean("HAS_CHANGES",true)
                editor.apply()
                fragmentSignin.finish()


//                } else{
//                    fragmentSignin.startActivity(Intent(fragmentSignin, FragmentDate::class.java));
//                }

                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception" + e)
                Toast.makeText(context, "Failed to save your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

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
    var percentof: String? = " "+Validator().profileRate(userDetails).toString()
        set(price) {
            progresPercentage.postValue(Validator().profileRate(userDetails));

            field = price
            notifyPropertyChanged(BR.percentof)
        }

//    @get:Bindable
//    var percentofProgress: Int = (Validator().profileRate(userDetails))
//        set(price) {
//            field = price
//            notifyPropertyChanged(BR.percentofProgress)
//        }

    @get:Bindable
    var photo: String? = userDetails.profilePic
        set(price) {
            field = price
            notifyPropertyChanged(BR.photo)
        }


    @get:Bindable
    var nameTitle: String? = userDetails.firstName
        set(price) {
            field = price
            notifyPropertyChanged(BR.nameTitle)
        }

    @get:Bindable
    var usernameTitle: String? = userDetails.username
        set(price) {
            field = price
            usernameTitleId = "reelme.co/"+ userDetails.username
            notifyPropertyChanged(BR.usernameTitle)
        }

    @get:Bindable
    var usernameTitleId: String? = "reelme.co/"+ userDetails.username
        set(price) {
            field = price
            notifyPropertyChanged(BR.usernameTitleId)
        }

    @get:Bindable
    var countryid: String? = userDetails.countryOrigin
        set(price) {
            field = price
            notifyPropertyChanged(BR.countryid)
        }


    @get:Bindable
    var instagramId: String? = userDetails.instagramLink
        set(price) {
            field = price
            notifyPropertyChanged(BR.instagramId)
        }

    @get:Bindable
    var youtubeId: String? = userDetails.youtube
        set(price) {
            field = price
            notifyPropertyChanged(BR.youtubeId)
        }

    @get:Bindable
    var emailTitle: String? = userDetails.emailId
        set(price) {
            field = price
            notifyPropertyChanged(BR.emailTitle)
        }

    @get:Bindable
    var phoneTitle: String? = userDetails.phoneNumber
        set(price) {
            field = price
            notifyPropertyChanged(BR.phoneTitle)
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
