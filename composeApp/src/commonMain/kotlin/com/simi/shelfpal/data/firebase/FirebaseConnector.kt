package com.simi.shelfpal.data.firebase

const val GEMINI_MODEL = "gemini-2.5-flash"
const val JSON_RESPONSE_MIMETYPE = "application/json"
const val PRODUCT_NAME = "name"
const val CATEGORY = "category"
const val EXPIRY_DAY = "expiryDay"
const val EXPIRY_MONTH = "expiryMonth"
const val EXPIRY_YEAR = "expiryYear"
const val HAS_DAY = "hasDay"

expect interface FirebaseConnector {
    suspend fun getProductBasicInfoFromImage(
        image: ByteArray,
        prompt: String
    ) : String?

    suspend fun getProductExpiryInfoFromImage(
        image: ByteArray,
        prompt: String = EXPIRY_PROMPT
    ) : String?
}

fun getBasicInfoPrompt(categories: List<String>) = "Get the product name and category from the image. " +
    "Return the response in JSON format using the provided schema fields. " +
    "Select the category from the following list: ${categories.joinToString(", ")}." +
    "The category MUST be from the list, if it doesn't match a category you can put it in the Other category."

// TODO: Add toast for fields that are unable to be gotten. e.g. Unable to get some expiry fields in the UI.
// TOD0: Update prompt not to assume. It must be strict for expiry dates, not adding batch numbers, etc.
const val EXPIRY_PROMPT = "Get the expiry day, month and year from the product image. The image MUST have a labeled expiry text or information" +
    "Return the response in JSON format using expiryDay, expiryMonth, expiryYear and hasDay fields." +
        "If the image does not have an expiry day, expiryDay field must be NULL and hasDay field must be false" +
        "If you're not able to infer the expiry information for any field, return NULL for that field."
