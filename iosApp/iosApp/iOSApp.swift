import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinHelper.companion.start()
    }

    var body: some Scene {
        let di = KoinHelper()
        WindowGroup {
            ContentView(viewModel: di.rocketLaunchViewModel)
        }
    }
}
