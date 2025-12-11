package org.ilerna.apidemoapp.ui.screen.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ilerna.apidemoapp.domain.model.DBCharacter
import org.ilerna.apidemoapp.domain.repository.CharacterRepository

/**
 * DetailsViewModel - ViewModel for fetching and managing character details
 */
class DetailsViewModel : ViewModel() {
    private val characterRepository = CharacterRepository()
    private val _character = MutableLiveData<DBCharacter?>()
    val character = _character

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _error = MutableLiveData<String?>()
    val error = _error

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite = _isFavorite

    /**
     * Fetch character details by ID
     */
    fun getCharacterById(characterId: Int) {
        _isLoading.value = true
        _error.value = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = characterRepository.getCharacterById(characterId)
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _character.value = response.body()
                        Log.d("DetailsViewModel", "Character loaded: ${response.body()?.name}")
                        // Check if character is favorite
                        checkIsFavorite(characterId)
                    } else {
                        _error.value = "Error: ${response.message()}"
                        Log.e("DetailsViewModel", "Error: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _error.value = "Exception: ${e.message}"
                    Log.e("DetailsViewModel", "Exception: ${e.message}")
                }
            }
        }
    }

    /**
     * Check if character is in favorites
     */
    private fun checkIsFavorite(characterId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val isFav = characterRepository.isFavorite(characterId)
                withContext(Dispatchers.Main) {
                    _isFavorite.value = isFav
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error checking favorite: ${e.message}")
            }
        }
    }

    /**
     * Toggle favorite status
     */
    fun toggleFavorite() {
        val char = _character.value ?: return
        val currentFavoriteStatus = _isFavorite.value ?: false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (currentFavoriteStatus) {
                    // Remove from favorites
                    characterRepository.deleteFavorite(char.id)
                    Log.d("DetailsViewModel", "Character removed from favorites: ${char.name}")
                } else {
                    // Add to favorites
                    characterRepository.saveAsFavorite(char)
                    Log.d("DetailsViewModel", "Character added to favorites: ${char.name}")
                }
                withContext(Dispatchers.Main) {
                    _isFavorite.value = !currentFavoriteStatus
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error toggling favorite: ${e.message}")
                withContext(Dispatchers.Main) {
                    _error.value = "Error updating favorites: ${e.message}"
                }
            }
        }
    }
}
