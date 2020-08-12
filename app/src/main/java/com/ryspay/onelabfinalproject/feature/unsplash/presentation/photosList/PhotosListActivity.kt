package com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotoClickListener
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotosAdapter
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.DetailActivity
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.search.SearchActivity
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
            viewModel.onSearchActivityNavigationEvent()
        }
    }

    private fun initRecycler() {
        photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            photoAdapter = PhotosAdapter(this@PhotosListActivity)
            adapter = photoAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)){
                        viewModel.onNextPage()
                    }
                    else if (!recyclerView.canScrollVertically(-1)){
                        viewModel.onPreviousPage()
                    }
                }
            })
        }
    }

    private fun initObservers(){
        viewModel.photos.observe(this, ::handlePhotosState)
        viewModel.navigationState.observe(this, ::handleNavigationState)
    }

    private fun handlePhotosState(state: PhotosState<List<PhotoItemUI>>){
        loaderLayout.isVisible = state is PhotosState.Loading
        when(state) {
            is PhotosState.Success -> fillRecycler(state.data)
            is PhotosState.Error -> showToast(state.message)
        }
    }

    private fun handleNavigationState(state: NavigationState) {
        when(state) {
            is NavigationState.ToDetailsActivity -> goToDetailsActivity(state.id)
            is NavigationState.ToSearchActivity -> goToSearchActivity()
        }
    }

    private fun fillRecycler(items: List<PhotoItemUI>){
        photoAdapter.submitList(items)
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