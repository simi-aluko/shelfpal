package com.simi.shelfpal

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

actual interface FirebaseConnector {
    actual suspend fun generateContent()
}

class FirebaseConnectorImpl : FirebaseConnector {
    override suspend fun generateContent() {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(GEMINI_MODEL)

        val prompt = "Write a story about a magic backpack."

        val response = model.generateContent(prompt)

        println("FirebaseAi: ${response.text}")
    }
}