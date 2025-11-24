package org.ilerna.apidemoapp.ui.screen.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ilerna.apidemoapp.domain.model.DBCharacter
import org.ilerna.apidemoapp.domain.repository.Repository

/**
 * DetailsViewModel - ViewModel for fetching and managing character details
 */
class DetailsViewModel : ViewModel() {
    private val repository = Repository()
    private val _character = MutableLiveData<DBCharacter?>()
    val character = _character

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _error = MutableLiveData<String?>()
    val error = _error

    /**
     * Fetch character details by ID
     */
    fun getCharacterById(characterId: Int) {
        _isLoading.value = true
        _error.value = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getCharacterById(characterId)
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _character.value = response.body()
                        Log.d("DetailsViewModel", "Character loaded: ${response.body()?.name}")
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
}
