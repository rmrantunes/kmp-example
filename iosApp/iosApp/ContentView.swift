import Shared
import SwiftUI

@MainActor
struct ContentView: View {
    let di = KoinHelper()

    @State private var state: RocketLaunchUiState = RocketLaunchUiStateLoading()

    var body: some View {
        RocketLaunchScreenView(viewModel: di.rocketLaunchViewModel)
    }
}
