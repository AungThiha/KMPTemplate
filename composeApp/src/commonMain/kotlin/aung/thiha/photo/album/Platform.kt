package aung.thiha.photo.album

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform