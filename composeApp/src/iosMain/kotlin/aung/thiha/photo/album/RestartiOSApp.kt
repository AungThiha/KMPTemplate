package aung.thiha.photo.album

object RestartHolder {
    var restarter : RestartiOSApp? = null
}

interface RestartiOSApp {
    fun restart()
}


