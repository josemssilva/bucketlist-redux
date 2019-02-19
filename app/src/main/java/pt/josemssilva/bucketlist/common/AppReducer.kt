package pt.josemssilva.bucketlist.common

import org.rekotlin.Action
import pt.josemssilva.bucketlist.modules.editable.EditableReducer
import pt.josemssilva.bucketlist.modules.editable.EditableState
import pt.josemssilva.bucketlist.modules.items.ItemsReducer

fun appReducer(action: Action, state: AppState?): AppState {
    val currentState = state ?: AppState()

    return AppState(
        items = ItemsReducer.reduce(action, currentState.items),
        editable = EditableReducer.reduce(action, currentState.editable)
    )
}