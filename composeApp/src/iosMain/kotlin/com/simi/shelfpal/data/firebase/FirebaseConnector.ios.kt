package com.simi.shelfpal.data.firebase

import dev.gitlive.firebase.storage.Data
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

actual interface FirebaseConnector {
    actual suspend fun getProductBasicInfoFromImage(
        image: ByteArray,
        prompt: String
    ) : String?

    actual suspend fun getProductExpiryInfoFromImage(
        image: ByteArray,
        prompt: String
    ): String?

    actual fun getFirebaseData(image: ByteArray): Data
}

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData {
    if (isEmpty()) return NSData()
    return this.usePinned { pinned ->
        NSData.dataWithBytes(
            bytes = pinned.addressOf(0),
            length = this.size.toULong()
        )
    }
}