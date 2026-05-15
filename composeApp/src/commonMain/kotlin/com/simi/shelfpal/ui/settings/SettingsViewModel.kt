package com.simi.shelfpal.ui.settings

import androidx.lifecycle.ViewModel
import com.simi.shelfpal.data.settings.ShelfPalSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : ViewModel(), KoinComponent {

    private val shelfPalSettings: ShelfPalSettings by inject()

    private var _notificationToggle = MutableStateFlow(shelfPalSettings.getNotificationToggle())
    val notificationToggle: StateFlow<Boolean> = _notificationToggle

    fun toggleNotification(updatedValue: Boolean) {
        shelfPalSettings.saveNotificationToggle(updatedValue)
        _notificationToggle.value = shelfPalSettings.getNotificationToggle()
    }

}