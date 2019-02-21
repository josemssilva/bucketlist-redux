package pt.josemssilva.bucketlist.modules.auth

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.User

object AuthReducer {
    fun reduce(action: Action, state: AuthState?) = when (action) {

        is AuthAction.Login -> performingLogin(state)
        is AuthAction.LoginSuccess -> loginSuccess(action.user, state)
        is AuthAction.LoginError -> loginFailure(action.errorMessage, state)
        is AuthAction.Logout -> performLogout()

        else -> state
    }

    private fun performingLogin(state: AuthState?): AuthState {
        return (state ?: AuthState()).copy(
            loginState = LoginState.PERFORMING,
            error = null
        )
    }

    private fun loginSuccess(user: User, state: AuthState?): AuthState {
        return (state ?: AuthState()).copy(
            loginState = LoginState.ACTIVE,
            user = user,
            error = null
        )
    }

    private fun loginFailure(error: String, state: AuthState?): AuthState {
        return (state ?: AuthState()).copy(
            loginState = LoginState.ERROR,
            error = error
        )
    }

    private fun performLogout() = AuthState(
        LoginState.LOGGED_OUT
    )
}