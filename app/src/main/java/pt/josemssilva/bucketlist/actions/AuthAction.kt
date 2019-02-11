package pt.josemssilva.bucketlist.actions

import org.rekotlin.Action
import pt.josemssilva.bucketlist.entities.Session

sealed class AuthAction : Action {
    sealed class Login {
        data class Perform(
            val username: String,
            val password: String
        ) : AuthAction()

        data class Success(val session: Session) : AuthAction()
        object Failure : AuthAction()
    }

    object Logout : AuthAction()
}