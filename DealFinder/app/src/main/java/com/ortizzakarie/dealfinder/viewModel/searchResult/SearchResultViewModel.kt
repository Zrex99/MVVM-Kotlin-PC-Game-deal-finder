package com.ortizzakarie.dealfinder.viewModel.searchResult

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ortizzakarie.dealfinder.model.repository.CheapSharkRepositoryInterface

/**
 * Created by Zakarie Ortiz on 1/11/21.
 */
class SearchResultViewModel @ViewModelInject constructor(
    private val repository: CheapSharkRepositoryInterface,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    //currentTitleQuery gets it's query string data from SavedStateHandle getLiveData,
    // this allows the search results & query to survive process death if the user leaves the app.
    private val currentTitleQuery = state.getLiveData(CURRENT_TITLE_QUERY, DEFAULT_TITLE_QUERY)

    //games is a reactive val that is "activated" whenever currentQuery has it's values changed.
    // the following function will call the repository to view the search results of the currentQuery string.
    val games = currentTitleQuery.switchMap { titleQueryString ->

        repository.searchGameByTitle(titleQueryString).cachedIn(viewModelScope)
    }

    //searchForGames is called whenever the user submits a search query.
    fun searchForGames(titleQuery: String) {
        if (titleQuery.isEmpty()) {
            return
        }
        currentTitleQuery.value = titleQuery
    }

    companion object {
        private const val CURRENT_TITLE_QUERY = "current_title_query"

        //TODO: Give the search results fragment a "search for game" UI element that is displayed when the fragment is opened for the first time.
        // this will happen when a home fragment is implemented.
        private const val DEFAULT_TITLE_QUERY = ""
    }
}