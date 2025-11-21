//
// Created by simileoluwa aluko on 08/11/2025.
//

import ComposeApp
import FirebaseAILogic

final class IosFirebaseConnector: FirebaseConnector {
    func generateContent() async throws {
        do {
            let ai = FirebaseAI.firebaseAI(backend: .googleAI())
            let model = ai.generativeModel(modelName: FirebaseConnectorKt.GEMINI_MODEL)
            let prompt = "Write a story about a magic backpack."
            let response = try await model.generateContent(prompt)
            print(response.text ?? "No text in response.")
        } catch {
            print("An error occured: \(error)")
            throw error
        }
    }
}
