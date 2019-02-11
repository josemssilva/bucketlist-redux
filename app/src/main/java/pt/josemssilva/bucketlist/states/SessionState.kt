package pt.josemssilva.bucketlist.states

import pt.josemssilva.bucketlist.entities.Session

data class SessionState(
    val session: Session = EMPTY
) {
    companion object {
        val EMPTY = Session()
    }

    fun isValid() = session != EMPTY
}