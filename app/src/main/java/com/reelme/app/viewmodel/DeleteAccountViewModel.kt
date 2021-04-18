package com.reelme.app.viewmodel

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.activities.LaunchActivity
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Constants
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.FragmentDate
import com.reelme.app.view.FragmentDeleteAccount
import java.util.regex.Matcher
import java.util.regex.Pattern


class DeleteAccountViewModel(private val context: Context, private val fragmentSignin: FragmentDeleteAccount) : BaseObservable(), NetworkChangeHandler.NetworkChangeListener {

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false



    init {
        networkHandler()
        getUserInfo()
    }

    fun signInUserClicked() {
       // fragmentSignin.finish()

//       if( isValidName(firstName!!) && isValidName(lastName!!)){
//           setUserInfo()
//       }
     //   Toast.makeText(context, "Yet to implement", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

        if(deleteUser){

            val user = FirebaseAuth.getInstance().currentUser

//            val intent = Intent(fragmentSignin, LaunchActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            fragmentSignin.startActivity(intent)

            val userUid = user!!.uid

            Toast.makeText(context, "Please wait... Delete user in progress", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }


            Log.d(TAG, "User to be deleted$userUid")

            // userDetails = UserModel();
            // setUserInfo()

            val myDB = FirebaseFirestore.getInstance()
            val collection = myDB.collection(Constants.BASEURL_COLLECTION_GEN_PROFILEINFO)

            collection.document(userUid)
                    .delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!")

                        user.delete().addOnSuccessListener {
                            Log.d(TAG, "User  deleted.$it")

                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "User account deleted.", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
                                Log.d(TAG, "User account deleted.")
                                clearUSerDAta();
                                val intent = Intent(fragmentSignin, LaunchActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                fragmentSignin.startActivity(intent)

                            }
                        }.addOnFailureListener {
                            Log.d(TAG, "Error deleting useraddOnFailureListener $it.message")
                            clearUSerDAta()
                            FirebaseAuth.getInstance().signOut();
                            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();

                            val intent = Intent(fragmentSignin, LaunchActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            fragmentSignin.startActivity(intent)


                            OnFailureListener { e ->
                                Log.d(TAG, "Error deleting user", e)
                            //    Toast.makeText(context, "Error while deleting ... please try again later", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                            }
                        }


                    }
                    .addOnFailureListener(OnFailureListener { e ->
                        Log.d(TAG, "Error deleting document", e)
                        Toast.makeText(context, "Error while deleting ... please try again later", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

                    })

        }
        else {
            userDetails.isDeactivated = true

            setUserInfo()

        }


    }

    fun clearUSerDAta(){
        val gsonValue = Gson().toJson("")

        val sharedPreference =  context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()

    }


    fun cancelDeleteClicked() {
        fragmentSignin.finish()
//        if( isValidName(firstName!!) && isValidName(lastName!!)){
//            setUserInfo()
//        }

    }

    fun onSplitTypeChanged(radioGroup: RadioGroup?, id: Int) {
        val rr = radioGroup!!.checkedRadioButtonId

        if(id == R.id.deactivate_acc){
            Log.d(TAG, "deactivate")
            deleteUser = false
        }else if(id == R.id.delete_acc){
            Log.d(TAG, "delete")
            deleteUser = true
        }

    }

    @get:Bindable
    var deleteUser: Boolean = false
        set(price) {
            field = price
            notifyPropertyChanged(BR.deleteUser)
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

    private fun getUserInfo() {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d(TAG, auth.emailId)
            userDetails = (auth as UserModel)
          //  signInUserClicked()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Exception")
        }
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


        FirbaseWriteHandlerActivity(fragmentSignin).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                progressBarVisible = View.INVISIBLE

                val intent = Intent(fragmentSignin, LaunchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                fragmentSignin.startActivity(intent)
                FirebaseAuth.getInstance().signOut();


                Log.d(TAG, "onSuccess")
                Toast.makeText(context, "we have successfully saved your profile", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                progressBarVisible = View.INVISIBLE
                Log.d(TAG, "Exception" + e)
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

//    @get:Bindable
//    var firstName: String? = ""
//        set(price) {
//            field = price
//            notifyPropertyChanged(BR.firstName)
//        }
//
//    @get:Bindable
//    var lastName: String? = ""
//        set(price) {
//            field = price
//            notifyPropertyChanged(BR.lastName)
//        }


    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }

    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    companion object {
        private const val TAG = "DeleteAccountViewModel"
    }
}


