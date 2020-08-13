package com.ryspay.onelabfinalproject.feature.unsplash.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotosAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.BuildConfig
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var photoAdapter: PhotosAdapter

    private val viewModel: SearchViewModel by viewModel()

    companion object{
        fun intent(context: Context): Intent{
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initListeners()

    }
    private fun initListeners() {
        search_text_yv.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                viewModel.searchAndPostPhotos(search_text_yv.text.toString(),null)
                return@OnKeyListener true
            }
            false
        })

        search_text_yv.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                viewModel.searchAndPostPhotos(search_text_yv.text.toString(),null)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}