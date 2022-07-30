package com.example.restaurantsapp.domain.usecases

import com.example.restaurantsapp.data.local.menu.Menus
import com.example.restaurantsapp.data.local.rest.Restaurant
import com.example.restaurantsapp.domain.models.SearchedItem
import com.example.restaurantsapp.domain.repository.GetSearchedItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class GetSearchedItemUseCase @Inject constructor(
    private val repository: GetSearchedItemRepository
) {

    private var searchedList: ArrayList<SearchedItem> = arrayListOf()
    private var listOfRestaurants: Array<Restaurant>? = arrayOf()
    var listOfMenus: Array<Menus>? = arrayOf()


    suspend fun getData(query: String): ArrayList<SearchedItem> {
        val parentJob = CoroutineScope(IO).launch {

            val job1 = launch {
                 listOfRestaurants = repository.getRestaurants()
            }

            val job2 = launch {
                 listOfMenus = repository.getMenuItems()
            }
        }
        parentJob.join()
        return searchForTheQuery(query)
    }

    private suspend fun searchForTheQuery(query: String): ArrayList<SearchedItem> {
        searchedList.clear()
        val parentJob = CoroutineScope(IO).launch {

                val job1 = launch {
                    listOfRestaurants?.map {

                        if ((it.cuisine_type.contains(query, true)) ||
                            (it.name.contains(query, true)) ||
                            (it.neighborhood.contains(query, true)) ||
                            (it.address.contains(query, true))
                        ) {
                            searchedList.add(SearchedItem(it.name, it.cuisine_type))
                        }
                    }
                }

                val job2 = launch {

                    listOfMenus?.map {menus ->
                        menus.categories.map {category ->
                            if(category.name.contains(query, true)){
                                searchedList.add(SearchedItem(category.name,"Your food is here" ))
                            }
                            category.menu_items.map { menuItem ->  

                                if((menuItem.name.contains(query, true)) ||
                                            menuItem.description.contains(query, true)||
                                            menuItem.price.contains(query, true)){
                                    searchedList.add(SearchedItem(menuItem.name, menuItem.description))
                                }
                            }
                        }
                        
                    }
                }
            }



        parentJob.join()
        return searchedList
    }
}