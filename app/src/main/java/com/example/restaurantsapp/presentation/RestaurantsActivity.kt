package com.example.restaurantsapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.restaurantsapp.R
import com.example.restaurantsapp.databinding.ActivityMainBinding
import com.example.restaurantsapp.domain.models.SearchedItem
import com.example.restaurantsapp.presentation.adapter.ItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantsActivity : AppCompatActivity() {


    private val viewModel by viewModels<RestaurantsViewModel>()

    private lateinit var binding: ActivityMainBinding
    private val itemsList = ArrayList<SearchedItem>()

    private var itemsAdapter: ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

       viewModel.setQuery("")

        lifecycleScope.launch {
            viewModel.searchedItemResponse.collectLatest {
                when (it) {
                    is SearchedItemUIState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorTextView.visibility = View.GONE
                    }

                    is SearchedItemUIState.Empty -> {

                    }
                    is SearchedItemUIState.Error -> {

                        binding.progressBar.visibility = View.GONE
                        binding.errorTextView.text = it.message

                    }
                    is SearchedItemUIState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        itemsList.add(it.data)
                        itemsAdapter?.notifyDataSetChanged()

                    }
                }
            }
        }

        binding.apply {
            recyclerView.setHasFixedSize(true)
            itemsAdapter = ItemsAdapter(this@RestaurantsActivity, itemsList)
            recyclerView.adapter = itemsAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_items, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    itemsList.clear()
                    viewModel.setQuery(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        return false
    }
}