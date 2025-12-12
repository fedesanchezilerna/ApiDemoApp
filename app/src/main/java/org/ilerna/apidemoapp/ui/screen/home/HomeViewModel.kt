package org.ilerna.apidemoapp.ui.screen.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ilerna.apidemoapp.domain.model.CharactersResponse
import org.ilerna.apidemoapp.domain.model.Links
import org.ilerna.apidemoapp.domain.model.Meta
import org.ilerna.apidemoapp.domain.repository.CharacterRepository

class HomeViewModel : ViewModel() {
    private val characterRepository = CharacterRepository()
    private val _characters = MutableLiveData<CharactersResponse>()
    val characters = _characters

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredCharacters = MutableLiveData<CharactersResponse>()
    val filteredCharacters = _filteredCharacters

    fun getCharacters(page: Int = 1, limit: Int = 100) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = characterRepository.getAllCharacters(page, limit)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _characters.value = response.body()
                    Log.d("APIViewModel", "Characters loaded: ${response.body()?.items?.size}")
                } else {
                    Log.e("APIViewModel", "Error: ${response.message()}")
                }
            }
        }
    }

    fun searchCharacters(query: String) {
        _searchQuery.value = query
        
        if (query.isBlank()) {
            _filteredCharacters.value = _characters.value
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val response = characterRepository.filterCharacters(name = query)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val charactersList = response.body()
                    // Wrap the list in CharactersResponse format
                    _filteredCharacters.value = CharactersResponse(
                        items = charactersList ?: emptyList(),
                        meta = _characters.value?.meta ?: Meta(
                            totalItems = charactersList?.size ?: 0,
                            itemCount = charactersList?.size ?: 0,
                            itemsPerPage = charactersList?.size ?: 0,
                            totalPages = 1,
                            currentPage = 1
                        ),
                        links = _characters.value?.links ?: Links(
                            first = "",
                            previous = "",
                            next = "",
                            last = ""
                        )
                    )
                    Log.d("APIViewModel", "Filtered characters: ${charactersList?.size}")
                } else {
                    Log.e("APIViewModel", "Search error: ${response.message()}")
                }
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _filteredCharacters.value = _characters.value
    }
}