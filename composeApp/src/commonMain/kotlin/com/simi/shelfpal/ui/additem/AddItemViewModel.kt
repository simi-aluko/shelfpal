package com.simi.shelfpal.ui.additem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.firebase.getBasicInfoPrompt
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.mp.KoinPlatform.getKoin

class AddItemViewModel: ViewModel(){
    val firebaseConnector by getKoin().inject<FirebaseConnector>()

    private val _aiProductBasicInfo = MutableStateFlow(ProductBasicInfo())
    val aiProductBasicInfo = _aiProductBasicInfo.asStateFlow()

    private val _aiProductExpiryInfo = MutableStateFlow(ProductExpiryInfo())
    val aiProductExpiryInfo = _aiProductExpiryInfo.asStateFlow()

    private var itemEntryValid = false

    fun getBasicInfoFromImage(
        image: ByteArray,
        categories: List<String>
    ) {
        viewModelScope.launch {
            try {
                val response = firebaseConnector.getProductBasicInfoFromImage(
                    image = image,
                    prompt = getBasicInfoPrompt(categories)
                )
                if (response != null) {
                    val productBasicInfo = Json.decodeFromString<ProductBasicInfo>(response)
                    _aiProductBasicInfo.value = productBasicInfo
                } else {
                    throw Exception("Null product basic info response from FirebaseAI")
                }
            } catch (e: Exception) {
                // todo: send error state
            }
        }
    }

    fun getExpiryInfoFromImage(
        image: ByteArray
    ) {
        viewModelScope.launch {
            try {
                val response = firebaseConnector.getProductExpiryInfoFromImage(
                    image = image
                )
                if (response != null) {
                    val productExpiryInfo = Json.decodeFromString<ProductExpiryInfo>(response)
                    _aiProductExpiryInfo.value = productExpiryInfo
                } else {
                    throw Exception("Null product expiry info response from FirebaseAI")
                }
            } catch (e: Exception) {
                // todo: send error state
            }
        }
    }

    fun saveItem() {
        // best way to send error state to fields

    }
}