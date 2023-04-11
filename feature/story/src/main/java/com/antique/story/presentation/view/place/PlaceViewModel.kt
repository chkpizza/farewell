package com.antique.story.presentation.view.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antique.common.util.ApiState
import com.antique.story.domain.model.Place
import com.antique.story.domain.usecase.FetchPlacesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaceViewModel @Inject constructor(
    private val fetchPlacesUseCase: FetchPlacesUseCase
) : ViewModel() {
    private val _places = MutableLiveData<ApiState<Place>>(ApiState.Loading)
    val places: LiveData<ApiState<Place>> get() = _places

    private lateinit var _key: String
    private lateinit var _query: String
    private var _page: Int = 1

    fun fetchPlaces(key: String, query: String) {
        viewModelScope.launch {
            try {
                _key = key
                _query = query
                _page = 1
                val response = fetchPlacesUseCase(_key, _query, _page)
                _places.value = ApiState.Success(response)
            } catch (e: Exception) {
                _places.value = ApiState.Error(e)
            }
        }
    }

    fun fetchMorePlaces() {
        viewModelScope.launch {
            try {
                _page++
                places.value?.let {
                    (it as? ApiState.Success)?.let { state ->
                        if(!state.items.isEnd) {
                            val response = fetchPlacesUseCase(_key, _query, _page)
                            val newList = state.items.places.toMutableList()
                            newList.addAll(response.places)
                            _places.value = ApiState.Success(Place(newList, response.isEnd))
                        }
                    }
                }
            } catch (e: Exception) {
                _places.value = ApiState.Error(e)
            }
        }
    }
}