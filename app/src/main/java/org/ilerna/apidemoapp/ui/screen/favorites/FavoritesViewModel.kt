package org.ilerna.apidemoapp.ui.screen.favorites

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
 * FavoritesViewModel - ViewModel for managing favorite characters
 */
class FavoritesViewModel : ViewModel() {
    private val repository = Repository()
    private val _favorites = MutableLiveData<List<DBCharacter>>()
    val favorites = _favorites

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    /**
     * Load all favorite characters from the database
     */
    fun loadFavorites() {
        _isLoading.value = true
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val favoritesEntities = repository.getFavorites()
                val favoritesList = favoritesEntities.map { entity ->
                    DBCharacter(
                        id = entity.id,
                        name = entity.name,
                        ki = entity.ki,
                        maxKi = entity.maxKi,
                        race = entity.race,
                        gender = entity.gender,
                        description = entity.description,
                        image = entity.image,
                        affiliation = entity.affiliation,
                        deletedAt = entity.deletedAt,
                        originPlanet = entity.originPlanet,
                        transformations = entity.transformations
                    )
                }
                
                withContext(Dispatchers.Main) {
                    _favorites.value = favoritesList
                    _isLoading.value = false
                    Log.d("FavoritesViewModel", "Favorites loaded: ${favoritesList.size}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    Log.e("FavoritesViewModel", "Error loading favorites: ${e.message}")
                }
            }
        }
    }
}
