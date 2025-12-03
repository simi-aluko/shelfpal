package com.simi.shelfpal

import android.graphics.BitmapFactory
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig

actual interface FirebaseConnector {
    actual suspend fun getProductBasicInfoFromImage(
        image: ByteArray,
        prompt: String
    ): String?

    actual suspend fun getProductExpiryInfoFromImage(
        image: ByteArray,
        prompt: String
    ): String?
}

class FirebaseConnectorImpl : FirebaseConnector {
    val ai = Firebase.ai(backend = GenerativeBackend.googleAI())

    override suspend fun getProductBasicInfoFromImage(
        image: ByteArray,
        prompt: String
    ): String? {
        val model = ai.generativeModel(
            modelName = GEMINI_MODEL,
            generationConfig = generationConfig {
                responseMimeType = JSON_RESPONSE_MIMETYPE
                responseSchema = basicInfoResponseSchema
            }
        )
        val content = content {
            image(BitmapFactory.decodeByteArray(image, 0, image.size))
            text(prompt)
        }

        return model.generateContent(content).text
    }

    override suspend fun getProductExpiryInfoFromImage(
        image: ByteArray,
        prompt: String
    ): String? {
        val model = ai.generativeModel(
            modelName = GEMINI_MODEL,
            generationConfig = generationConfig {
                responseMimeType = JSON_RESPONSE_MIMETYPE
                responseSchema = expiryInfoResponseSchema
            }
        )

        val content = content {
            image(BitmapFactory.decodeByteArray(image, 0, image.size))
            text(prompt)
        }

        return model.generateContent(content).text
    }
}

private val basicInfoResponseSchema = Schema.obj(
    mapOf(
        PRODUCT_NAME to Schema.string(),
        CATEGORY to Schema.string()
    )
)

private val expiryInfoResponseSchema = Schema.obj(
    mapOf(
        EXPIRY_DAY to Schema.integer(nullable = true),
        EXPIRY_MONTH to Schema.integer(nullable = true),
        EXPIRY_YEAR to Schema.integer(nullable = true),
        HAS_DAY to Schema.boolean()
    )
)