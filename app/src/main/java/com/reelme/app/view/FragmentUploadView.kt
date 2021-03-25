package com.reelme.app.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.databinding.FragmentUpoadBinding
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.listeners.StringResultListener
import com.reelme.app.listeners.UseInfoGeneralResultListener
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.viewmodel.UploadListModel


class FragmentUploadView : AppCompatActivity() {

    var TAG = "FragmentUploadView"

    lateinit var binding: FragmentUpoadBinding

    lateinit var activity  : Activity


    @Transient
    lateinit internal var areaViewModel: UploadListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentUpoadBinding = DataBindingUtil.setContentView(this, R.layout.fragment_upoad)
        areaViewModel = UploadListModel(this, this)
        binding.yearList = areaViewModel
        activity = this
        binding.yearList!!.initFileChooser().observe(this, { showFileChooser() })
        getUserInfo()
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }

    private val FILE_SELECT_CODE = 0

    private fun showFileChooser() {
//        val intent = Intent()
//        intent.action = Intent.ACTION_OPEN_DOCUMENT
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = "*/*"
//        val extraMimeTypes = arrayOf("application/pdf", "application/doc")
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes)
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, FILE_SELECT_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var path = ""
        if (resultCode === RESULT_OK) {
            if (requestCode === FILE_SELECT_CODE) {
                val clipData = data!!.getClipData()
                //null and not null path
                if (clipData == null) {
                    path += data.data
                    FirbaseWriteHandlerActivity(this).uploadToStorage(data.data, object: StringResultListener {
                        override fun onSuccess(url: String) {

                            userDetails.profilePic = url
                            setUserInfo()
                        }

                        override fun onFailure(e: Exception) {
                        }
                    });

                } else {
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        val uri = item.uri
                        path += uri.toString() + "\n"
                        Log.d("dump", "dump" + path)
                        FirbaseWriteHandlerActivity(this).uploadToStorage(uri, object: StringResultListener {
                            override fun onSuccess(url: String) {
                                userDetails.profilePic = url
                                setUserInfo()
                            }

                            override fun onFailure(e: Exception) {
                            }
                        });

                    }
                }
            }
        }
    }



    lateinit var userDetails : UserModel

    private fun getUserInfo() {
        val sharedPreference = getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)

            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    private fun setUserInfo(){

        Toast.makeText(activity, "we are saving your profile picture", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

       // binding!!.progressbar.visibility= View.VISIBLE
        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

        FirbaseWriteHandlerActivity(this).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
              //  binding!!.progressbar.visibility= View.INVISIBLE
                startActivity(Intent(activity, FragmentBioMobile::class.java));
                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(activity, "we have successfully saved your profile piture", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
             //   binding!!.progressbar.visibility= View.INVISIBLE
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception"+e)
                Toast.makeText(activity, "Failed to save your profile.. please try again later", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }
}