package com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.NetworkState
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotoClickListener
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotosAdapter
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.DetailActivity
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.search.SearchActivity
import com.ryspay.onelabfinalproject.utils.Const.Companion.ORDER_BY_LATEST
import com.ryspay.onelabfinalproject.utils.Const.Companion.ORDER_BY_POPULAR
import kotlinx.android.synthetic.main.activity_photos_list.*
import kotlinx.android.synthetic.main.loading_template.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosListActivity : AppCompatActivity(), PhotoClickListener {

    private lateinit var photoAdapter: PhotosAdapter

    private val viewModel: PhotosListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_list)

        initListeners()
        initRecycler()
        initObservers()
    }

    private fun initListeners(){
        searchFabBtn.setOnClickListener {
//            viewModel.onSearchActivityNavigationEvent()
            viewModel.onRefresh(ORDER_BY_POPULAR)
        }
    }

    private fun initRecycler() {
        photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            photoAdapter = PhotosAdapter(this@PhotosListActivity)
            adapter = photoAdapter
        }
    }

    private fun initObservers(){
        viewModel.navigationState.observe(this, ::handleNavigationState)
        viewModel.pagingState.observe(this, ::handlePagingState)
        viewModel.pagedPhotos.observe(this, ::handlePagedPhotos)
    }

    private fun handlePagedPhotos(list: PagedList<PhotoItemUI?>){
        photoAdapter.submitList(list)
    }

    private fun handleNavigationState(state: NavigationState) {
        when(state) {
            is NavigationState.ToDetailsActivity -> goToDetailsActivity(state.id)
            is NavigationState.ToSearchActivity -> goToSearchActivity()
        }
    }

    private fun handlePagingState(state: NetworkState){
        loaderLayout.isVisible = state is NetworkState.Loading
        when(state){
            is NetworkState.Success -> showToast("page loaded")
            is NetworkState.Error -> showToast(state.msg)
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToDetailsActivity(id: String){
        startActivity(DetailActivity.intent(this, id))
    }

    private fun goToSearchActivity(){
        startActivity(SearchActivity.intent(this))
    }

    override fun onPhotoCLick(id: String) {
        viewModel.onDetailActivityNavigationEvent(id)
    }
}