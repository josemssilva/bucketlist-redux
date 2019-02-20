package pt.josemssilva.bucketlist.common

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.modules.auth.AuthState
import pt.josemssilva.bucketlist.modules.editable.EditableState
import pt.josemssilva.bucketlist.modules.items.ItemsState

data class AppState(
    val items: ItemsState? = null,
    val editable: EditableState? = null,
    val auth: AuthState? = null
) : StateType