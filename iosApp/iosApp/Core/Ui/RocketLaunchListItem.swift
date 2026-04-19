//
// Created by Rafael Antunes on 16/04/26.
//

import Foundation
import SwiftUI
import Shared

struct RocketLaunchListItem: View {
    var rocketLaunch: ModelRocketLaunch

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("\(rocketLaunch.missionName) - \(String(rocketLaunch.launchYear))").font(.system(size: 18)).bold()
                Text(launchText).foregroundColor(launchColor)
                Text("Launch year: \(String(rocketLaunch.launchYear))")
                Text("\(rocketLaunch.details ?? "")")

            }
            Spacer()
        }
    }
}

extension RocketLaunchListItem {
    private var launchText: String {
        if let isSuccess = rocketLaunch.launchSuccess {
            return isSuccess.boolValue ? "Success" : "Failure"
        } else {
            return "No data"
        }
    }

    private var launchColor: Color {
        if let isSuccess = rocketLaunch.launchSuccess {
            return isSuccess.boolValue ? Color.green :Color.red
        } else {
            return Color.gray
        }
    }
}
