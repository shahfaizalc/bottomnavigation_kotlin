package com.reelme.app.viewmodel

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.FacebookSdk.getApplicationContext
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.listeners.StringResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.view.FragmentBioMobile
import com.reelme.app.view.FragmentUploadView


class UploadListModel(internal var context: Context,
                      internal var fragment: FragmentUploadView) :
        NetworkChangeHandler.NetworkChangeListener, BaseObservable() {

    val TAG = "ExamYearsListModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var openChooserLiveData = MutableLiveData<Boolean>()

    private var openChooserCamera = MutableLiveData<Boolean>()

    private var skipLiveData = MutableLiveData<Boolean>()

    private var imageUrl : Uri = Uri.EMPTY

    public fun setImageUrl(url: Uri){
        imageUrl = url
    }
    init {
        networkHandler()
        registerListeners()
        getUserInfo()
    }

    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    @get:Bindable
    var retry = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.retry)
        }

    @get:Bindable
    var msgView = View.GONE
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msgView)
        }

    lateinit var userDetails : UserModel
    private var isEdit = false;


    @get:Bindable
    var skipVisible = if (isEdit) View.GONE else  View.VISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.skipVisible)
        }



    @get:Bindable
    var progressBarVisible = View.INVISIBLE
        set(progressBarVisible) {
            field = progressBarVisible
            notifyPropertyChanged(BR.progressBarVisible)
        }

    @get:Bindable
    var msg: String? = null
        set(msg) {
            field = msg
            notifyPropertyChanged(BR.msg)
        }

    fun retryBtnClick() = View.OnClickListener {

    }

    fun signInUserClicked() = View.OnClickListener {
        //  fragmentSignin.finish()
        Log.d("Authentication urll", imageUrl.toString())

        if(!imageUrl.toString().isEmpty()){
            progressBarVisible = View.VISIBLE
            FirbaseWriteHandlerActivity(fragment).coompressjpeg(imageUrl, object : StringResultListener {
                override fun onSuccess(url: String) {
                    Log.d("coompressjpeg onSuccess url", url)
                    Toast.makeText(fragment, " Upload Successful", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                    progressBarVisible = View.INVISIBLE

                    userDetails.profilePic = url

                    setUserInfo()
                }

                override fun onFailure(e: Exception) {
                    Toast.makeText(fragment, "Failed to upload your profile.. please try again later", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                    progressBarVisible = View.INVISIBLE

                }
            });
        } else{
            Toast.makeText(fragment, "Please Take Picture ", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

        }



        //   fragment.startActivity(Intent(fragment, FragmentBioMobile::class.java));
    }

    fun initFileChooser(): LiveData<Boolean> {
        return openChooserLiveData
    }


    fun openFileChosserCamera() = View.OnClickListener {

        alertDialog()
      //  openChooserCamera.setValue(true);
    }

    fun initFileChooserCamera(): LiveData<Boolean> {
        return openChooserCamera
    }


    fun onSkipButtonClicked() = View.OnClickListener {
        skipLiveData.setValue(true);
    }

    fun initSkip(): LiveData<Boolean> {
        return skipLiveData
    }


//    fun onSkipButtonClicked() {
//        // fragmentSignin.finish()
//        Toast.makeText(context,"Yet to Do", Toast.LENGTH_SHORT).apply {setGravity(Gravity.TOP, 0, 0); show() }
//    }

    fun registerListeners() {
        networkStateHandler?.registerNetWorkStateBroadCast(context)
        networkStateHandler?.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler?.unRegisterNetWorkStateBroadCast(context)
    }


    override fun networkChangeReceived(state: Boolean) {

        when (state) {
            true -> msgView = View.GONE
            false -> {
                msgView = View.VISIBLE
                msg = context.resources.getString(R.string.network_ErrorMsg)
                networkError()
            }
        }
    }

    private fun networkError() {
        Toast.makeText(context, context.resources.getText(R.string.network_ErrorMsg), Toast.LENGTH_SHORT).apply {setGravity(Gravity.TOP, 0, 0); show() }
    }




    private fun getUserInfo() {
        val sharedPreference = fragment.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT", false)

        if(isEdit){
            skipVisible = View.GONE
            //  binding.skipBtnUpload.visibility = View.GONE
        }

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)

            userDetails = (auth as UserModel)
            if(!isEdit && !userDetails.profilePic.isNullOrEmpty()){
                fragment.startActivity(Intent(fragment, FragmentBioMobile::class.java))
            }

        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }


    private fun setUserInfo(){
        progressBarVisible = View.VISIBLE

        Toast.makeText(fragment, "we are saving your profile picture", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

        // binding!!.progressbar.visibility= View.VISIBLE
        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  fragment.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.putBoolean("IS_EDIT", false)
        editor.apply()

        FirbaseWriteHandlerActivity(fragment).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                //  binding!!.progressbar.visibility= View.INVISIBLE
                if (isEdit) {
                    fragment.setResult(2, Intent())
                    fragment.finish()
                } else {
                    // GenericValues().navigateToNext(fragment)
                    fragment.startActivity(Intent(fragment, FragmentBioMobile::class.java));
                }

                //  fragment.startActivity(Intent(fragment, FragmentBioMobile::class.java));
                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(fragment, "we have successfully saved your profile piture", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                progressBarVisible= View.INVISIBLE
            }

            override fun onFailure(e: Exception) {
                //   binding!!.progressbar.visibility= View.INVISIBLE
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception" + e)
                Toast.makeText(fragment, "Failed to save your profile.. please try again later", Toast.LENGTH_LONG).apply { setGravity(Gravity.TOP, 0, 0); show() }
                progressBarVisible= View.INVISIBLE

            }
        })


    }


    private fun alertDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(fragment)
        builder.setTitle("")
        builder.setMessage("Choose Image")

        //Yes Button

        //Yes Button
        builder.setPositiveButton("Camera", DialogInterface.OnClickListener { dialog, which ->
            Log.i("Code2care ", "Camera button Clicked!")
            dialog.dismiss()
            openChooserCamera.setValue(true);

        })

        //No Button

        //No Button
        builder.setNegativeButton("Gallery", DialogInterface.OnClickListener { dialog, which ->
            Log.i("Code2care ", "Gallery button Clicked!")
            dialog.dismiss()
            openChooserLiveData.setValue(true)
        })
        //Cancel Button
        //Cancel Button
        builder.setNeutralButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            Log.i("Code2care ", "Cancel button Clicked!")
            dialog.dismiss()
        })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}