package org.ilerna.apidemoapp.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ilerna.apidemoapp.data.repository.SettingsRepository
import org.ilerna.apidemoapp.domain.repository.CharacterRepository

/**
 * SettingsViewModel - Manages settings state including theme preference
 * Persists settings using SettingsRepository and SharedPreferences
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val characterRepository = CharacterRepository()
    
    private val _currentTheme = MutableStateFlow(loadThemeFromPreferences())
    private val _currentCharactersViewMode = MutableStateFlow(loadViewModeFromPreferences())
    val currentTheme: StateFlow<ThemeMode> = _currentTheme.asStateFlow()
    val currentCharactersViewMode: StateFlow<CharacterViewMode> = _currentCharactersViewMode.asStateFlow()

    /**
     * Load theme mode from SharedPreferences
     */
    private fun loadThemeFromPreferences(): ThemeMode {
        val themeName = settingsRepository.getSettingValue(
            SettingsRepository.KEY_THEME_MODE,
            ThemeMode.SYSTEM.name
        )
        return try {
            ThemeMode.valueOf(themeName)
        } catch (_: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }

    /**
     * Load view mode from SharedPreferences
     */
    private fun loadViewModeFromPreferences(): CharacterViewMode {
        val viewModeName = settingsRepository.getSettingValue(
            SettingsRepository.KEY_VIEW_MODE,
            CharacterViewMode.LIST.name
        )
        return try {
            CharacterViewMode.valueOf(viewModeName)
        } catch (_: IllegalArgumentException) {
            CharacterViewMode.LIST
        }
    }

    /**
     * Set theme mode and persist to SharedPreferences
     */
    fun setTheme(theme: ThemeMode) {
        _currentTheme.value = theme
        settingsRepository.saveSettingValue(SettingsRepository.KEY_THEME_MODE, theme.name)
    }

    /**
     * Set characters view mode and persist to SharedPreferences
     */
    fun setCharactersView(viewMode: CharacterViewMode) {
        _currentCharactersViewMode.value = viewMode
        settingsRepository.saveSettingValue(SettingsRepository.KEY_VIEW_MODE, viewMode.name)
    }

    /**
     * Delete all favorite characters from the database
     */
    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            characterRepository.deleteAllFavorites()
        }
    }
}

/**
 * Factory for creating SettingsViewModel with SettingsRepository dependency
 */
class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
