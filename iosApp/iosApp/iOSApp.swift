import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinHelper.companion.start()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
