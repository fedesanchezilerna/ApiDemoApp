package org.ilerna.apidemoapp.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ilerna.apidemoapp.domain.repository.Repository

/**
 * SettingsViewModel - Manages settings state including theme preference
 */
class SettingsViewModel : ViewModel() {
    private val repository = Repository()
    
    private val _currentTheme = MutableStateFlow(ThemeMode.SYSTEM)
    private val _currentCharactersViewMode = MutableStateFlow(CharacterViewMode.LIST)
    val currentTheme: StateFlow<ThemeMode> = _currentTheme.asStateFlow()
    val currentCharactersViewMode: StateFlow<CharacterViewMode> = _currentCharactersViewMode.asStateFlow()

    /**
     * Set theme mode
     */
    fun setTheme(theme: ThemeMode) {
        _currentTheme.value = theme
    }

    /**
     * Set characters view mode
     */
    fun setCharactersView(viewMode: CharacterViewMode) {
        _currentCharactersViewMode.value = viewMode
    }

    /**
     * Delete all favorite characters from the database
     */
    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
        }
    }
}
