package aung.thiha.photo.album

import java.net.InetAddress

class JVMPlatform: Platform {
    override val name: String = InetAddress.getLocalHost().getHostName()
}

actual fun getPlatform(): Platform = JVMPlatform()
