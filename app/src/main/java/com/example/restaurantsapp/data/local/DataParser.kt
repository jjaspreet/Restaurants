package com.example.restaurantsapp.data.local

import android.content.Context
import com.example.restaurantsapp.R
import com.example.restaurantsapp.data.local.menu.Category
import com.example.restaurantsapp.data.local.menu.Menus
import com.example.restaurantsapp.data.local.rest.Restaurant
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.text.ParseException
import javax.inject.Inject

class DataParser @Inject constructor(
    private val gson: Gson,
    private val context: Context
){

    suspend fun parseRestaurants(): Array<Restaurant>? {
        val jsonFile: InputStream = context.resources.openRawResource(R.raw.retrts)
        return gson.fromJson(InputStreamReader(jsonFile), Array<Restaurant>::class.java)
    }

    suspend fun parseMenuItems(): Array<Menus>? {
        val jsonFile: InputStream = context.resources.openRawResource(R.raw.menus)
        return gson.fromJson(InputStreamReader(jsonFile), Array<Menus>::class.java)
    }


}