package com.example.restaurantsapp.data.repository

import com.example.restaurantsapp.data.local.DataParser
import com.example.restaurantsapp.data.local.menu.Category
import com.example.restaurantsapp.data.local.menu.Menus
import com.example.restaurantsapp.data.local.rest.Restaurant
import com.example.restaurantsapp.domain.repository.GetSearchedItemRepository
import javax.inject.Inject

class GetSearchedItemRepository @Inject constructor(

    private val dataParser: DataParser

) : GetSearchedItemRepository {

    override suspend fun getRestaurants(): Array<Restaurant>? {

        return dataParser.parseRestaurants()

    }

    override suspend fun getMenuItems(): Array<Menus>? {

        return dataParser.parseMenuItems()
    }
}