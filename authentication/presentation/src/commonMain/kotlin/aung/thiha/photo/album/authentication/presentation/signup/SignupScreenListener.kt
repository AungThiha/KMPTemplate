package aung.thiha.photo.album.authentication.presentation.signup

interface SignupScreenListener {
    fun navigateUp()
    fun updateEmail(email: String)
    fun updatePassword(password: String)
    fun updateConfirmPassword(confirmPassword: String)
    fun signup()
}