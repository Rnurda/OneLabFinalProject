package com.ryspay.onelabfinalproject.feature.unsplash.presentation.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.entity.DetailPhotoItemUI
import kotlinx.android.synthetic.main.loading_template.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel{
        parametersOf(
            intent?.getStringExtra(PHOTO_ID_INTENT)
        )
    }

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

        initObserver()
    }

    private fun initObserver(){
        viewModel.detailPhoto.observe(this, ::handleDetails)
    }

    private fun handleDetails(state: DetailState<DetailPhotoItemUI>){
        loaderLayout.isVisible = state is DetailState.Loading
        when(state) {
            is DetailState.Error -> showToast(state.message)
            is DetailState.Success -> fillData(state.data)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.d("DetailActivity", message)
    }

    private fun fillData(photo: DetailPhotoItemUI) {
        //ToDo
    }
}