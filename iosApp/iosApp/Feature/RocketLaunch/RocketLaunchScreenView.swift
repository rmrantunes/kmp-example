//
//  RocketLaunchScreenView.swift
//  iosApp
//
//  Created by Rafael Antunes on 16/04/26.
//

import SwiftUI
import Shared

@MainActor
struct RocketLaunchScreenView: View {
    @State private(set) var viewModel: IRocketLaunchViewModel

    @State private var state: RocketLaunchUiState = RocketLaunchUiStateLoading()

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
    }

    private func listView() -> AnyView {
        switch onEnum(of: self.state) {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .success(let success):
            return AnyView(
                List(success.launches, id: \.id) { launch in
                    RocketLaunchListItem(rocketLaunch: launch)
                }
            )
        case .fail(let fail):
            let message: String =
                fail.message ?? "Something went wrong. Try again later."
            return AnyView(Text(message).multilineTextAlignment(.center))

        }
    }

    private func load() {
        Task {
            self.viewModel.load()
        }
    }
}

extension ModelRocketLaunch: Identifiable {}
