package pt.josemssilva.bucketlist.modules.auth

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.User

sealed class AuthAction : Action {
    data class Login(
        val username: String,
        val password: String
    ) : AuthAction()

    data class LoginSuccess(val user: User) : AuthAction()

    data class LoginError(
        val errorMessage: String
    ) : AuthAction()

    object Logout : AuthAction()
}