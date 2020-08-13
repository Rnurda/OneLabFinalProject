package com.ryspay.onelabfinalproject.feature.unsplash.presentation.search

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ryspay.onelabfinalproject.R
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.NetworkState
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotoClickListener
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.adapter.PhotosAdapter
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.DetailActivity
import com.ryspay.onelabfinalproject.utils.Const
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.loading_template.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity(), PhotoClickListener {

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

        initRecycler()
        initListeners()
        initObservers()
    }

    private fun initRecycler() {
        searchRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            photoAdapter = PhotosAdapter(this@SearchActivity)
            adapter = photoAdapter
        }
    }

    private fun initListeners() {
        search_text_yv.setOnEditorActionListener { textView, i, keyEvent ->

            if (i == EditorInfo.IME_ACTION_SEARCH){
                /**
                 * onRefresh( query , orderBy ) orderBy = {ORDER_BY_LATEST, ORDER_BY_RELEVANT}
                 * */
                Log.d("SearchActivity.kt", "Requested")
                viewModel.onRefresh(search_text_yv.text.toString(), Const.ORDER_BY_LATEST)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initObservers(){
        viewModel.navigationState.observe(this, ::handleNavigationState)
        viewModel.pagingState.observe(this, ::handlePagingState)
        viewModel.pagedSearchPhotos.observe(this, ::handlePagedPhotos)
    }

    private fun handlePagedPhotos(list: PagedList<PhotoItemUI?>){
        photoAdapter.submitList(list)
    }

    private fun handleNavigationState(state: NavigationState) {
        when(state) {
            is NavigationState.ToDetailsActivity -> goToDetailsActivity(state.id)
        }
    }

    private fun goToDetailsActivity(id: String) {
        startActivity(DetailActivity.intent(this, id))
    }

    private fun handlePagingState(state: NetworkState){
        loaderLayout.isVisible = state is NetworkState.Loading
        when(state){
            is NetworkState.Success -> showToast("page loaded")
            is NetworkState.Error -> showToast(state.msg)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPhotoCLick(id: String) {
        viewModel.onDetailActivityNavigationEvent(id)
    }
}