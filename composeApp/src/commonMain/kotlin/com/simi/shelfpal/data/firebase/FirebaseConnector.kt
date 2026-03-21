package com.simi.shelfpal.data.firebase

import dev.gitlive.firebase.storage.Data

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

    suspend fun getExpirySummary(
        prompt: String
    ) : String?

    fun getFirebaseData(image: ByteArray): Data
}
