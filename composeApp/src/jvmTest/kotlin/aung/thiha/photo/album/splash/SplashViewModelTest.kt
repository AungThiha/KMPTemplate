package aung.thiha.photo.album.splash

import aung.thiha.coroutines.TestDispatcherExtension
import aung.thiha.photo.album.authentication.data.remote.response.TokenCheckResponse
import aung.thiha.photo.album.authentication.data.remote.service.FakeAuthenticationDataSource
import aung.thiha.photo.album.authentication.presentation.signup.navigation.SigninRoute
import aung.thiha.photo.album.di.core.KoinTestExtension
import aung.thiha.photo.album.navigation.runNavTest
import aung.thiha.photo.album.navigation.shouldNavigateTo
import aung.thiha.photo.album.navigation.withClearBackStack
import aung.thiha.photo.album.photos.presentation.navigation.PhotoListRoute
import aung.thiha.session.domain.FakeSessionStorage
import aung.thiha.session.domain.model.Session
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.component.get
import org.koin.test.KoinTest
import org.koin.test.inject

@ExtendWith(TestDispatcherExtension::class)
@ExtendWith(KoinTestExtension::class)
class SplashViewModelTest : KoinTest {

    private val sessionStorage: FakeSessionStorage by inject()
    private val authDataSource: FakeAuthenticationDataSource by inject()

    @Test
    fun `navigates to photo list if signed in`() = runNavTest { spyNav ->
        sessionStorage.session = Session("access", "refresh", "user")
        authDataSource.tokenCheckResponses += TokenCheckResponse(message = "all good")

        get<SplashViewModel>()

        spyNav shouldNavigateTo PhotoListRoute.withClearBackStack
    }

    @Test
    fun `navigates to signin if not signed in`() = runNavTest { spyNav ->
        sessionStorage.session = null

        get<SplashViewModel>()

        spyNav shouldNavigateTo SigninRoute.withClearBackStack
    }

    @Test
    fun `navigates to signin if server says token invalid`() = runNavTest { spyNav ->
        sessionStorage.session = Session("access", "refresh", "user")

        get<SplashViewModel>()

        spyNav shouldNavigateTo SigninRoute.withClearBackStack
    }
}
