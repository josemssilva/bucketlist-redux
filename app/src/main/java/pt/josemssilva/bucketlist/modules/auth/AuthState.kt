package pt.josemssilva.bucketlist.modules.auth

import pt.josemssilva.bucketlist.data.entities.User

data class AuthState(
    val loginState: LoginState = LoginState.LOGGED_OUT,
    val user: User? = null,
    val error: String? = null
)

enum class LoginState {
    ACTIVE,
    LOGGED_OUT,
    PERFORMING,
    ERROR
}