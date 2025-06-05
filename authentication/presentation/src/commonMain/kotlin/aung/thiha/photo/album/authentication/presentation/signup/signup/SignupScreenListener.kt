package aung.thiha.photo.album.authentication.presentation.signup.signup

interface SignupScreenListener {
    fun onUpClick()
    fun onEmailChange(email: String)
    fun onPasswordChange(password: String)
    fun onConfirmPasswordChange(confirmPassword: String)
    fun onSignupClick()
}