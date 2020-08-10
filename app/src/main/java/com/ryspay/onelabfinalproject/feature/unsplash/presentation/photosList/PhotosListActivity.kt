package com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotosAdapter
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import kotlinx.android.synthetic.main.activity_photos_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosListActivity : AppCompatActivity() {

    private lateinit var photoAdapter: PhotosAdapter

    private val viewModel: PhotosListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_list)

        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        photos_recycler_view.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            photoAdapter = PhotosAdapter()
            adapter = photoAdapter
        }
    }

    private fun initObservers(){
        viewModel.photos.observe(this, ::handleState)
    }

    private fun handleState(state: PhotosState<List<PhotoItemUI>>){
        when(state) {
            is PhotosState.Success -> fillRecycler(state.data)
        }
    }

    private fun fillRecycler(items: List<PhotoItemUI>){
        photoAdapter.submitList(items)
    }
}