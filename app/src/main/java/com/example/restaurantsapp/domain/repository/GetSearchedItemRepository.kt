package com.example.restaurantsapp.domain.repository

import com.example.restaurantsapp.data.local.menu.Category
import com.example.restaurantsapp.data.local.menu.Menu
import com.example.restaurantsapp.data.local.menu.Menus
import com.example.restaurantsapp.data.local.rest.Restaurant

interface GetSearchedItemRepository {

    suspend fun getRestaurants(): Array<Restaurant>?

    suspend fun getMenuItems(): Array<Menus>?
}