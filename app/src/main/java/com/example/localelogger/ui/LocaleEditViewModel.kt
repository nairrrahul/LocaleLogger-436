package com.example.localelogger.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.localelogger.data.LocaleDatabase
import com.example.localelogger.data.LocaleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocaleEditViewModel(application: Application, private val localeId: Int) :  AndroidViewModel(application) {

    private val db = LocaleDatabase.getDatabase(application)
    private val localeDao = db.localeDao()

    private val _selectedIcon = MutableStateFlow("")
    val selectedIcon: StateFlow<String> = _selectedIcon

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _location = MutableStateFlow("Fetching location...")
    val location: StateFlow<String> = _location

    private val _timestamp = MutableStateFlow("")
    val timestamp: StateFlow<String> = _timestamp

    init {
        loadLocale()
        Log.e("Icon", selectedIcon.value);
        Log.e("Description", description.value);
        Log.e("UnderScore Desc", _description.value);
    }

    private fun loadLocale() {
        viewModelScope.launch {
            localeDao.getLocaleById(localeId)?.let { locale ->
                _selectedIcon.value = locale.iconType
                _title.value = locale.title
                _description.value = locale.description
                _location.value = locale.location
                _timestamp.value = locale.timestamp
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun saveLocale() {
        viewModelScope.launch {
            localeDao.update(
                LocaleEntity(
                    id = localeId,
                    iconType = _selectedIcon.value,
                    title = _title.value,
                    description = _description.value,
                    location = _location.value,
                    timestamp = _timestamp.value
                )
            )
        }
    }

    companion object {
        fun Factory(application: Application, localeId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.AndroidViewModelFactory(application) {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(LocaleEditViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return LocaleEditViewModel(application, localeId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}