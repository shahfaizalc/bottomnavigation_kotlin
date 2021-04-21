package com.reelme.app.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentUpoadBinding
import com.reelme.app.viewmodel.UploadListModel


class FragmentUploadView : AppCompatActivity() {

    var TAG = "FragmentUploadView"

    lateinit var binding: FragmentUpoadBinding

    lateinit var activity: Activity


    @Transient
    lateinit internal var areaViewModel: UploadListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_upoad)
        areaViewModel = UploadListModel(this, this)
        binding.yearList = areaViewModel
        activity = this
        binding.yearList!!.initFileChooser().observe(this, { showFileChooser() })

        binding.yearList!!.initSkip().observe(this, {
            startActivity(Intent(activity, FragmentBioMobile::class.java));
        })
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }


    private fun checkIfAlreadyhavePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }


    private val FILE_SELECT_CODE = 0

    private fun showFileChooser() {

        val galleryPermissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (checkIfAlreadyhavePermission()) {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, FILE_SELECT_CODE)

        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    2)
        }
    }

//        val clipData = data!!.clipData
//
//        val selectedImage: Uri = data.data!!
//        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = contentResolver.query(selectedImage, filePathColumn, null, null, null)
//        cursor!!.moveToFirst()
//        val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
//        val picturePath = cursor!!.getString(columnIndex)
//        cursor.close()
//
//
//        Log.d(TAG, "dump12221 $picturePath");
//
//        binding.profileimg.setImageBitmap(BitmapFactory.decodeFile(picturePath))
//
//


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var path = ""
        if (resultCode === RESULT_OK) {
            if (requestCode === FILE_SELECT_CODE) {
                val clipData = data!!.clipData
                //null and not null path
                if (clipData == null) {
                    path += data.data

                    val selectedImage: Uri = data.data!!
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(selectedImage, filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    val picturePath = cursor.getString(columnIndex)
                    cursor.close()


                    Log.d(TAG, "dump12221 $picturePath");

                    binding.profileimg.setImageBitmap(BitmapFactory.decodeFile(picturePath))

                    binding.yearList!!.setImageUrl(data.data!!)

                } else {
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)

                        val uri = item.uri
                        path += uri.toString() + "\n"
                        Log.d(TAG, "dump  $item.uri.path")

                        val selectedImage: Uri = data.data!!
                        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                        val cursor = contentResolver.query(selectedImage, filePathColumn, null, null, null)
                        cursor!!.moveToFirst()
                        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                        val picturePath = cursor.getString(columnIndex)
                        cursor.close()


                        Log.d(TAG, "dump12221 $picturePath");

                        binding.profileimg.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                        binding.yearList!!.setImageUrl(data.data!!)

                    }
                }
            }
        }
    }


}