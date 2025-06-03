package aung.thiha.photo.album.authentication.presentation.signup.signin

interface SigninScreenListener {
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun signin()
    fun navigateToSignup()
}