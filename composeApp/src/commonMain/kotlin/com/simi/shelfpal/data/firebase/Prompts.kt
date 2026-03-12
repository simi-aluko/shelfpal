package com.simi.shelfpal.data.firebase

import com.simi.shelfpal.data.models.ExpirySummary
import kotlinx.serialization.json.Json

/**
 * Prompt to extract a products basic info from an image
 */
fun getBasicInfoPrompt(categories: List<String>) = "Get the product name and category from the image. " +
        "Return the response in JSON format using the provided schema fields. " +
        "Select the category from the following list: ${categories.joinToString(", ")}." +
        "The category MUST be from the list, if it doesn't match a category you can put it in the Other category."

/**
 * Prompt to extract a products expiry info from an image
 */
const val EXPIRY_PROMPT = "Get the expiry day, month and year from the product image. The image MUST have a labeled expiry text or information" +
        "Return the response in JSON format using expiryDay, expiryMonth, expiryYear and hasDay fields." +
        "If the image does not have an expiry day, expiryDay field must be NULL and hasDay field must be false" +
        "If you're not able to infer the expiry information for any field, return NULL for that field."

/**
 * Prompt to summarize soon to expire items
 */
fun getExpirySummaryPrompt(expirySummary: ExpirySummary) =
    "Provide an app user friendly summary of these shelf items. They will be expiring soon and " +
    "we want to provide the user with actionable tips of what to do with these items. " +
    "This summary will be displayed in a card within an app. Some items are expiring in a month, " +
    "some others in a week, some in a day, and the others in a custom set time. " +
    "Prioritize items expiring very soon in the tips. Items: ${Json.encodeToString(expirySummary)}"
