package com.example.restaurantsapp.presentation

import com.example.restaurantsapp.domain.models.SearchedItem

sealed class SearchedItemUIState{
    data class Success(val data : SearchedItem): SearchedItemUIState()
    data class Error(val message: String) : SearchedItemUIState()
    object Loading : SearchedItemUIState()
}
