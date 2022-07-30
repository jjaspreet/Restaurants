package com.example.restaurantsapp.presentation

import android.text.TextUtils
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.domain.models.SearchedItem
import com.example.restaurantsapp.domain.usecases.GetSearchedItemUseCase
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val useCase: GetSearchedItemUseCase
) : ViewModel(){

    private var _searchedItemResponse =
        MutableStateFlow<SearchedItemUIState>(SearchedItemUIState.Loading)
    val searchedItemResponse: StateFlow<SearchedItemUIState> = _searchedItemResponse
    private val DEFAULT_QUERY = "Man"
    var queryString: String = DEFAULT_QUERY


    fun getSearchedData(query: String){

        viewModelScope.launch {

            useCase.getData(query).onEach {

                _searchedItemResponse.value = SearchedItemUIState.Success(it)
            }

        }


    }

    fun setQuery(query: String){
        if(!TextUtils.isEmpty(query)){
            queryString = query
        }
        getSearchedData(queryString)
    }



}