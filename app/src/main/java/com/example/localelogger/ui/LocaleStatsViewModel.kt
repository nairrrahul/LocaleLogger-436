package com.example.localelogger.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.localelogger.data.LocaleDatabase
import com.example.localelogger.data.MonthCount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocaleStatsViewModel(application: Application) :  AndroidViewModel(application) {

    private val db = LocaleDatabase.getDatabase(application)
    private val localeDao = db.localeDao()

    private val _isNetCount = MutableStateFlow(true)
    val isNetCount: StateFlow<Boolean> = _isNetCount

    val netCounts = localeDao.getNetCount().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _monthCounts = MutableStateFlow<List<MonthCount>>(emptyList())
    val monthCounts: StateFlow<List<MonthCount>> = _monthCounts

    init {
        loadMonthCounts()
    }

    fun getMonthName(month: String): String {
        val months = listOf(
            "Jan.", "Feb.", "Mar.", "Apr.", "May", "June",
            "July", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."
        )

        val monthIndex = month.toIntOrNull()?.let { it - 1 }
        return if (monthIndex != null && monthIndex in 0..11) months[monthIndex] else "Invalid month"
    }

    private fun loadMonthCounts() {
        viewModelScope.launch {
            localeDao.getMonthCount().collect { counts ->
                _monthCounts.value = counts.map { monthCount ->
                    val (month, year) = monthCount.monthYear.split("/")
                    val monthName = getMonthName(month)
                    val formattedMonthYear = "$monthName $year"
                    monthCount.copy(monthYear = formattedMonthYear)
                }
            }
        }
    }

    fun toggleState() {
        _isNetCount.value = !_isNetCount.value
    }

}