package com.simi.shelfpal.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ExpirySummary(
    val expiringInAMonth: List<ExpirySummaryItem>,
    val expiringInAWeek: List<ExpirySummaryItem>,
    val expiringToDay: List<ExpirySummaryItem>,
    val expiringInCustomDuration: List<ExpirySummaryItem>,
)

@Serializable
data class ExpirySummaryItem(
    val name: String,
    val expiryDate: String,
)
