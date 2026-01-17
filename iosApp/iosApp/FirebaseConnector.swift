//
// Created by simileoluwa aluko on 08/11/2025.
//

import ComposeApp
import FirebaseAILogic
import UIKit

final class IosFirebaseConnector: FirebaseConnector {
    
    private lazy var ai = FirebaseAI.firebaseAI(backend: .googleAI())
    private lazy var model = ai.generativeModel(modelName: FirebaseConnectorKt.GEMINI_MODEL)
    
    private let basicInfoResponseSchema = Schema.object(
        properties: [
            FirebaseConnectorKt.PRODUCT_NAME : .string(),
            FirebaseConnectorKt.CATEGORY : .string()
        ]
    )
    
    private let expiryInfoResponseSchema = Schema.object(
        properties: [
            FirebaseConnectorKt.EXPIRY_DAY : .integer(nullable: true),
            FirebaseConnectorKt.EXPIRY_MONTH : .integer(nullable: true),
            FirebaseConnectorKt.EXPIRY_YEAR : .integer(nullable: true),
            FirebaseConnectorKt.HAS_DAY : .boolean()
        ]
    )
    
    func getProductBasicInfoFromImage(image: KotlinByteArray, prompt: String) async throws -> String? {
        let model = ai.generativeModel(
            modelName: FirebaseConnectorKt.GEMINI_MODEL,
            generationConfig: GenerationConfig(
                responseMIMEType: FirebaseConnectorKt.JSON_RESPONSE_MIMETYPE,
                responseSchema: basicInfoResponseSchema
            )
        )
        
        guard let image = UIImage(data: image.toNSData()) else {
            throw ImageDecodingError.invalidImageData
        }
        
        do {
            let response = try await model.generateContent(image, prompt)
            return response.text
        } catch {
            throw error
        }
    }
    
    func getProductExpiryInfoFromImage(image: KotlinByteArray, prompt: String) async throws -> String? {
        let model = ai.generativeModel(
            modelName: FirebaseConnectorKt.GEMINI_MODEL,
            generationConfig: GenerationConfig(
                responseMIMEType: FirebaseConnectorKt.JSON_RESPONSE_MIMETYPE,
                responseSchema: expiryInfoResponseSchema
            )
        )
        
        guard let image = UIImage(data: image.toNSData()) else {
            throw ImageDecodingError.invalidImageData
        }
        
        do {
            let response = try await model.generateContent(image, prompt)
            return response.text
        } catch {
            throw error
        }
    }

    enum ImageDecodingError: Error {
        case invalidImageData
    }
    
    func getFirebaseData(image: KotlinByteArray) -> Firebase_storageData {
        return Firebase_storageData(data: image.toNSData())
    }
}
