import SwiftUI
import FirebaseCore
import ComposeApp
import FirebaseMessaging

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
      
        NotifierManager.shared.initialize(
            configuration: NotificationPlatformConfigurationIos(
                showPushNotification: true, askNotificationPermissionOnStart: true, notificationSoundName: nil
            )
        )

        return true
    }
    
    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
          Messaging.messaging().apnsToken = deviceToken
    }
}

@main
struct iOSApp: App {
    
    // register app delegate for Firebase setup
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate


    init() {
        KoinModules().doInitKoin(
            firebaseConnector: IosFirebaseConnector()
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
