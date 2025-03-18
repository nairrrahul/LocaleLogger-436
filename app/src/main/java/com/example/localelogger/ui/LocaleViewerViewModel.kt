package com.example.localelogger.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localelogger.data.LocaleDatabase
import com.example.localelogger.data.LocaleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class LocaleViewerViewModel(application: Application) : AndroidViewModel(application) {
    private val _localesGrouped = MutableStateFlow<Map<String, List<LocaleEntity>>>(emptyMap())
    val localesGrouped: StateFlow<Map<String, List<LocaleEntity>>> = _localesGrouped

    private val db = LocaleDatabase.getDatabase(application)
    private val localeDao = db.localeDao()

    init {
        viewModelScope.launch {
            localeDao.getAllGroupedByMonth().collect { locales ->
                _localesGrouped.value = groupLocalesByMonth(locales)
            }
        }
    }

    private fun groupLocalesByMonth(locales: List<LocaleEntity>): Map<String, List<LocaleEntity>> {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val groupedMap = LinkedHashMap<String, MutableList<LocaleEntity>>()

        for (locale in locales) {
            val date = dateFormat.format(SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).parse(locale.timestamp)!!)
            groupedMap.getOrPut(date) { mutableListOf() }.add(locale)
        }

        return groupedMap
    }
}
