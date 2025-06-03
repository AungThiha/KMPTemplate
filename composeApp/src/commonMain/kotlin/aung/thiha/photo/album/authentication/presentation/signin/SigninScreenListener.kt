package aung.thiha.photo.album.authentication.presentation.signin

interface SigninScreenListener {
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun signin()
    fun navigateToSignup()
}
