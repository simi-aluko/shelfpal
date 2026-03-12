package com.simi.shelfpal.data.models

data class ExpirySummary(
    val expiringInAMonth: List<ExpirySummaryItem>,
    val expiringInAWeek: List<ExpirySummaryItem>,
    val expiringInADay: List<ExpirySummaryItem>,
    val expiringToDay: List<ExpirySummaryItem>,
    val expiringInCustomDuration: List<ExpirySummaryItem>,
)

data class ExpirySummaryItem(
    val name: String,
    val expiryDate: String,
)
