package com.reelme.app.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentUpoadBinding
import com.reelme.app.viewmodel.UploadListModel
import java.io.File


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
        binding.yearList!!.initFileChooserCamera().observe(this, { showFileChooserCamera() })


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


    private val FILE_SELECT_CODE = 101
    private val FILE_SELECT_CODE_CAMERA = 100


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

    var fileSelected = File("")
    var fileSelectedUri = Uri.EMPTY
    private fun showFileChooserCamera() {

        val galleryPermissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (checkIfAlreadyhavePermission()) {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val isDeviceSupportCamera: Boolean = this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
            if (isDeviceSupportCamera) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    fileSelected = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments")!!.path,
                            System.currentTimeMillis().toString() + ".jpg")
//            fileUri = Uri.fromFile(file)
                   var fileUri = FileProvider.getUriForFile(this, this.applicationContext.packageName + ".provider", fileSelected)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    }

                    startActivityForResult(takePictureIntent, FILE_SELECT_CODE_CAMERA)
                }

            } else {
                Toast.makeText(this, "camera not supported", Toast.LENGTH_SHORT).show()
            }

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


        Log.d(TAG, "dump12221 $fileSelected.absolutePath");
        Log.d(TAG, "dump12221 ${fileSelected.length()}");


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
            else if (requestCode === FILE_SELECT_CODE_CAMERA){

                    val myBitmap = BitmapFactory.decodeFile(fileSelected.absolutePath)
                    binding.profileimg.setImageBitmap(myBitmap)
                    binding.yearList!!.setImageUrl(Uri.fromFile(fileSelected))


                Log.d(TAG, "dump12221 ${fileSelected.absolutePath}");


            }
        }
    }


}