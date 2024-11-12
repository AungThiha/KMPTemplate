package aung.thiha.photo.album

import android.app.Activity
import android.content.Intent
import android.os.Build
import aung.thiha.photo.album.provider.ContextHolder

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
