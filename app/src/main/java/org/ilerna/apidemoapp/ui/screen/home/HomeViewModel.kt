package org.ilerna.apidemoapp.ui.screen.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ilerna.apidemoapp.domain.model.CharactersResponse
import org.ilerna.apidemoapp.domain.repository.Repository

class HomeViewModel : ViewModel() {
    private val repository = Repository()
    private val _characters = MutableLiveData<CharactersResponse>()
    val characters = _characters

    fun getCharacters(page: Int = 1, limit: Int = 100) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllCharacters(page, limit)
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
}