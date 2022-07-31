package com.example.restaurantsapp.presentation

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.domain.usecases.GetSearchedItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val useCase: GetSearchedItemUseCase
) : ViewModel() {

    private var _searchedItemResponse =
        MutableStateFlow<SearchedItemUIState>(SearchedItemUIState.Loading)
    val searchedItemResponse: StateFlow<SearchedItemUIState> = _searchedItemResponse
    private val DEFAULT_QUERY = "Man"
    var queryString: String = DEFAULT_QUERY


    fun getSearchedData(query: String) {

        viewModelScope.launch {

            useCase.getData(query).onEach {

                _searchedItemResponse.value = SearchedItemUIState.Success(it)


            }

        }


    }

    fun setQuery(query: String) {
        if (!TextUtils.isEmpty(query)) {
            queryString = query
        }
        getSearchedData(queryString)
    }


}