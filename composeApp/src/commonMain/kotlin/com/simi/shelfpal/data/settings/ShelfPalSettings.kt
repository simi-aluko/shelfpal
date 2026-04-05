package com.simi.shelfpal.data.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.simi.shelfpal.data.models.ExpirySummary
import com.simi.shelfpal.ui.updates.AiExpirySummary
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.KoinComponent

private const val EXPIRY_SUMMARY_KEY = "expiry_summary"
private const val EXPIRY_AI_SUMMARY = "expiry_ai_summary"

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
}