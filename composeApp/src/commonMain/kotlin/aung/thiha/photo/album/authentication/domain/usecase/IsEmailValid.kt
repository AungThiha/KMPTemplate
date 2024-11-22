package aung.thiha.photo.album.authentication.domain.usecase


fun isEmailValid(email: String): Boolean {
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex())
}