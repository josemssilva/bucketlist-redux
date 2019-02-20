package pt.josemssilva.bucketlist.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.josemssilva.bucketlist.data.entities.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepository(
    private val firebaseAuthInstance: FirebaseAuth
) {

    suspend fun loggedUser(): User? {
        return firebaseAuthInstance.currentUser?.toUser()
    }

    suspend fun performLogin(username: String, password: String): User {
        return suspendCoroutine { continuation ->
            firebaseAuthInstance.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    continuation.resume(it.user.toUser())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(
                        LoginException(it.localizedMessage)
                    )
                }
        }
    }

    suspend fun performLogout(): Boolean {
        firebaseAuthInstance.signOut()
        return true
    }
}

class LoginException(errorMessage: String) : Exception(errorMessage)

internal fun FirebaseUser.toUser(): User {
    return User(
        id = this.uid,
        name = this.displayName ?: "",
        username = this.email ?: ""
    )
}