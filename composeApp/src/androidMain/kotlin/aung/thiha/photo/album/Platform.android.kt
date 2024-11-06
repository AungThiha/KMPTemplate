package aung.thiha.photo.album

import android.app.Activity
import android.content.Intent
import android.os.Build
import aung.thiha.photo.album.provider.ContextHolder

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun restartApp() {
    val context = ContextHolder.context
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    intent?.apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
    if (context is Activity) {
        context.finish()
        Runtime.getRuntime().exit(0) // Optional, to ensure the app fully restarts
    }
}