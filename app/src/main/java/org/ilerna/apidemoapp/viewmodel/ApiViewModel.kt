package org.ilerna.apidemoapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ilerna.apidemoapp.model.PeopleData
import org.ilerna.apidemoapp.repository.Repository

class APIViewModel : ViewModel() {
    private val repository = Repository()
    private val _characters = MutableLiveData<PeopleData>()
    val characters = _characters

    fun getCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllCharacters()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _characters.value = response.body()
                } else {
                    Log.e("Error :", response.message())
                }
            }
        }
    }
}