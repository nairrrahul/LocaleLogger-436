package com.example.localelogger.ui

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localelogger.data.LocaleDao
import com.example.localelogger.data.LocaleDatabase
import com.example.localelogger.data.LocaleEntity
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocaleAddViewModel(application: Application) : AndroidViewModel(application) {

    private val db = LocaleDatabase.getDatabase(application)
    private val localeDao = db.localeDao()

    private val _selectedIcon = MutableStateFlow("ocean")
    val selectedIcon: StateFlow<String> = _selectedIcon

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _location = MutableStateFlow("Fetching location...")
    val location: StateFlow<String> = _location

    private val _timestamp = MutableStateFlow(getCurrentTimestamp())
    val timestamp: StateFlow<String> = _timestamp

    fun selectIcon(icon: String) {
        _selectedIcon.value = icon
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    @SuppressLint("MissingPermission")
    fun fetchLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            _location.value = location?.let {
                "${it.latitude}, ${it.longitude}"
            } ?: "Location unavailable"
        }
    }

    fun saveLocale() {
        viewModelScope.launch {
            val locale = LocaleEntity(
                iconType = _selectedIcon.value,
                title = _title.value,
                description = _description.value,
                location = _location.value,
                timestamp = _timestamp.value
            )
            localeDao.insert(locale)
            resetFields()
        }
    }

    private fun resetFields() {
        _selectedIcon.value = "ocean"
        _title.value = ""
        _description.value = ""
        _timestamp.value = getCurrentTimestamp()
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}