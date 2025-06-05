package aung.thiha.photo.album.authentication.presentation.signup.signin

interface SigninScreenListener {
    fun onEmailChange(email: String)
    fun onPasswordChange(password: String)
    fun onSigninClick()
    fun onSignupClick()
}