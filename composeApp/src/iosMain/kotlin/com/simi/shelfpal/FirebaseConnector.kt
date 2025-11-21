package com.simi.shelfpal

actual interface FirebaseConnector {
    actual suspend fun generateContent()
}