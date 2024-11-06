import ComposeApp
import Foundation
import SwiftUI
import UIKit

class ResartiOSAppImpl: NSObject, RestartiOSApp {
    func restart() {
        guard let window = UIApplication.shared.windows.first else { return }

        // Replace this line with your app's entry point if not using a storyboard.
        let rootViewController = ResartiOSAppImpl.createRootViewController()

        window.rootViewController = rootViewController
        window.makeKeyAndVisible()
    }

    private static func createRootViewController() -> UIViewController {
        // Create the initial view controller programmatically here
        // For example, if using Compose Multiplatform, wrap it in a UIHostingController.
        return UIHostingController(rootView: ContentView()) // Replace `ComposeView` with your actual root SwiftUI or Compose view
    }
}