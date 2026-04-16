import SwiftUI
import Shared

@MainActor
struct ContentView: View {
    @State private(set) var viewModel: IRocketLaunchViewModel
    
    @State private var state = RocketLaunchScreenState(
        isLoading: false, launches: []
    )

    var body: some View {
        NavigationView {
            listView()
                .navigationBarTitle("SpaceX Launches")
                .toolbar {
                    ToolbarItem(placement: .topBarTrailing) {
                        Button("Reload") {
                            load()
                        }
                    }
                }
        }
        .task {
            for await state in self.viewModel.state {
                self.state = state
            }
        }
        .task {
            try? await self.viewModel.load()
        }
    }

    private func listView() -> AnyView {
        if(state.isLoading) {
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        } else {
            return AnyView(List(state.launches) {launch in
                RocketLaunchRow(rocketLaunch: launch)
            })
        }
    }
    
    private func load() {
        Task {
            try? await self.viewModel.load()
        }
    }
}

extension RocketLaunch: Identifiable {}
