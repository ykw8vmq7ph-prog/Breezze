package com.music.breezze.iosApp

import android.os.Build
import androidx.compose.ui.platform.ComposeUIViewController
import com.music.breezze.ui.AppUi
import org.jetbrains.skia.GraphicsContext
import platform.Foundation.NSBundle
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDelegate
import platform.UIKit.UIColor
import platform.UIKit.UIWindow
import platform.os.objcObject
import kotlinx.cinterop.*
import platform.os.objcObject

class MainViewController : ComposeUIViewController() {
    override fun ViewDidLoad() {
        super.ViewDidLoad()
        
        // Initialize Skia graphics context for iOS
        GraphicsContext.usePlatformResources()
        
        // Set the content
        setContent {
            AppUi(
                windowWidth = 390.dp, // Default iPhone width
                modifier = androidx.compose.ui.Modifier.fillMaxSize()
            )
        }
    }
    
    override var prefersStatusBarHidden: Boolean = false
    override var preferredStatusBarStyle: Int = 0 // UIStatusBarStyleDefault
}

class AppDelegate : UIApplicationDelegate() {
    var window: platform.UIKit.UIWindow? = null
    
    override fun application(
        application: UIApplication,
        didFinishLaunchingWithOptions: platform.Foundation.NSDictionary?
    ): Boolean {
        // Initialize Skia
        GraphicsContext.usePlatformResources()
        
        // Create window
        val screen = platform.UIKit.UIScreen.mainScreen
        window = UIWindow(screen.bounds)
        
        // Create and set root view controller
        val mainVC = MainViewController()
        window?.rootViewController = mainVC
        window?.makeKeyAndVisible()
        
        return true
    }
    
    override fun applicationDidBecomeActive(application: UIApplication) {
        // App became active
    }
    
    override fun applicationWillResignActive(application: UIApplication) {
        // App will resign active
    }
    
    override fun applicationDidEnterBackground(application: UIApplication) {
        // App entered background
    }
    
    override fun applicationWillEnterForeground(application: UIApplication) {
        // App will enter foreground
    }
    
    override fun applicationWillTerminate(application: UIApplication) {
        // App will terminate
    }
}

// Entry point for iOS
@OptIn(ExperimentalForeignApi::class)
fun main() {
    // This is the entry point for the iOS app
    // The actual app launch is handled by UIApplicationMain in the generated Xcode project
}