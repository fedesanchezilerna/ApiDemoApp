package org.ilerna.apidemoapp.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ilerna.apidemoapp.CharacterApplication
import org.ilerna.apidemoapp.domain.repository.Repository

/**
 * SettingsViewModel - Manages settings state including dark mode preference
 */
class SettingsViewModel : ViewModel() {
    private val repository = Repository()
    
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    /**
     * Toggle dark mode on/off
     */
    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    /**
     * Set dark mode explicitly
     */
    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
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
