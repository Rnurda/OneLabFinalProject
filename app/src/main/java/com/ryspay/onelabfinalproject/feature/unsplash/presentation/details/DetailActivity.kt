package com.ryspay.onelabfinalproject.feature.unsplash.presentation.details

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.entity.DetailPhotoItemUI
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.loading_template.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel{
        parametersOf(
            intent?.getStringExtra(PHOTO_ID_INTENT)
        )
    }

    private var photoDownloadUrl: String? = null

    companion object{
        const val PHOTO_ID_INTENT = "photo_id"

        fun intent(context: Context, id: String): Intent {
            return Intent(context, DetailActivity::class.java).also {
                it.putExtra(PHOTO_ID_INTENT, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.isHideable = false

        initListeners()
        initObserver()
    }

    private fun initObserver(){
        viewModel.photoDetailPhoto.observe(this, ::handleDetails)
        viewModel.detailState.observe(this, androidx.lifecycle.Observer {
            when(it){
                is DetailState.DownloadImageState -> saveUrlImage(it.url)
            }
        })
    }
    
    private fun initListeners(){
        ic_download.setOnClickListener {
            photoDownloadUrl?.let{
                viewModel.onDownloadClicked(it)
            }
        }
    }

    private fun handleDetails(statePhoto: PhotoDetailState<DetailPhotoItemUI>){
        loaderLayout.isVisible = statePhoto is PhotoDetailState.Loading
        when(statePhoto) {
            is PhotoDetailState.Error -> showToast(statePhoto.message)
            is PhotoDetailState.Success -> fillData(statePhoto.data)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.d("DetailActivity", message)
    }

    private fun fillData(photo: DetailPhotoItemUI) {
        likes_tv.text = "${photo.likes} likes"
        downloads_tv.text = "${photo.downloads} downloads"
        if(!photo.city.isBlank()) {
            photo_taken_from_tv.text = "Photo taked in ${photo.city}, ${photo.country}"
        }
        description_tv.text = photo.description

        Glide.with(this)
            .load(photo.img_url)
            .centerCrop()
            .fitCenter()
            .into(detailPhotoImageView)

        photoDownloadUrl = photo.img_url
    }

    private fun saveUrlImage(url: String) {
        if (isExternalStorageWritable()) {
            saveImage(url)
        } else {
            showToast("Don't have permission")
        }
    }

    private fun saveImage(url: String) {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fname = "Unsplush_$timeStamp"
        downloadImageNew(fname, url)
    }

    /* Checks if external storage is available for read and write */
    private fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    private fun downloadImageNew(
        filename: String,
        downloadUrlOfImage: String
    ) {
        try {
            val dm =
                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri: Uri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )
            dm.enqueue(request)
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            Log.d("saveIamge", e.message?:"")
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }
}