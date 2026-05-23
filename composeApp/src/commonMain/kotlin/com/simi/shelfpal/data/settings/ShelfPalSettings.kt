package com.simi.shelfpal.data.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.simi.shelfpal.data.models.ExpirySummary
import com.simi.shelfpal.ui.updates.AiExpirySummary
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.core.component.KoinComponent

private const val EXPIRY_SUMMARY_KEY = "expiry_summary"
private const val EXPIRY_AI_SUMMARY = "expiry_ai_summary"
private const val NOTIFICATION_TOGGLE = "notification_toggle"
private const val FILTERS_KEY = "filters"

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class ShelfPalSettings : KoinComponent {

    private val settings = Settings()

    fun getExpirySummary() =
        settings.decodeValueOrNull(ExpirySummary.serializer(), EXPIRY_SUMMARY_KEY)

    fun saveExpirySummary(summary: ExpirySummary) {
        settings.encodeValue(ExpirySummary.serializer(), EXPIRY_SUMMARY_KEY, summary)
    }

    fun getAiSummary() = settings.decodeValueOrNull(AiExpirySummary.serializer(), EXPIRY_AI_SUMMARY)

    fun saveAiSummary(summary: AiExpirySummary) {
        settings.encodeValue(AiExpirySummary.serializer(), EXPIRY_AI_SUMMARY, summary)
    }

    fun saveUpdateItemIds(ids: Set<Long>, key: String) {
        settings.encodeValue(SetSerializer(Long.serializer()), key, ids)
    }

    fun getUpdateItemIds(key: String) = settings.decodeValueOrNull(SetSerializer(Long.serializer()), key)

    fun saveNotificationToggle(value: Boolean) {
        settings.putBoolean(NOTIFICATION_TOGGLE, value)
    }

    fun getNotificationToggle() = settings.getBoolean(NOTIFICATION_TOGGLE, true)
}