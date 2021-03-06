package com.ortizzakarie.dealfinder.viewModel.gameDetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ortizzakarie.dealfinder.model.dataModels.GameLookupResponse
import com.ortizzakarie.dealfinder.model.repository.CheapSharkRepositoryInterface
import com.ortizzakarie.dealfinder.utils.Resource
import kotlinx.coroutines.launch

/**
 * Created by Zakarie Ortiz on 1/27/21.
 */
class GameDetailsViewModel @ViewModelInject constructor(
    private val repository: CheapSharkRepositoryInterface
) : ViewModel() {

    private val _gameDetails = MutableLiveData<Resource<GameLookupResponse>>()
    val gameDetails: LiveData<Resource<GameLookupResponse>> = _gameDetails

    //searchForGameDetails will be called as soon as this view model class is binded to the fragment, and has access to the gameId.
    fun searchForGameDetails(idQuery: Int) {
        _gameDetails.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchGameDetailsByGameId(idQuery)
            _gameDetails.value = response

        }
    }

    // The API sends back any "deal" regardless if it is an actual deal or not,
    // to combat this, this function will compare the deals and if there are any deals that the discount price matches the retail price, then remove that deal.
    fun checkGameDetailDealsForFalseDeals(deals: MutableList<GameLookupResponse.GameDealResponse>) : List<GameLookupResponse.GameDealResponse> {
        return deals.filter { it.price != it.retailPrice }
    }


}