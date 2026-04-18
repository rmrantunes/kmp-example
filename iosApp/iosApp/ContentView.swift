import Shared
import SwiftUI

@MainActor
struct ContentView: View {
    let di = KoinHelper()
    @StateObject private var viewModelStoreOwner = IosViewModelStoreOwner()


    var body: some View {
        let viewModel: RocketLaunchViewModel = viewModelStoreOwner.viewModel(factory: di.rocketLaunchViewModelFactory)
        RocketLaunchScreenView(viewModel: viewModel)
    }
}
