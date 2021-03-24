package com.reelme.app.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.reelme.app.R
import com.reelme.app.databinding.FragmentUpoadBinding
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import com.reelme.app.viewmodel.UploadListModel


class FragmentUploadView : Activity() {

    var TAG = "FragmentUploadView"

    lateinit var binding: FragmentUpoadBinding


    @Transient
    lateinit internal var areaViewModel: UploadListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentUpoadBinding = DataBindingUtil.setContentView(this, R.layout.fragment_upoad)
        areaViewModel = UploadListModel(this, this)
        binding.yearList = areaViewModel

        binding.yearList!!.initFileChooser().observe(this, { showFileChooser() })
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
                    FirbaseWriteHandlerActivity(this).uploadToStorage(data.data);

                } else {
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        val uri = item.uri
                        path += uri.toString() + "\n"
                        Log.d("dump", "dump" + path)
                        FirbaseWriteHandlerActivity(this).uploadToStorage(uri);

                    }
                }
            }
        }
    }
}