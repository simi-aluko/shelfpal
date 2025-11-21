package com.simi.shelfpal

const val GEMINI_MODEL = "gemini-2.5-flash"

expect interface FirebaseConnector {
    suspend fun generateContent()
}