package pt.josemssilva.bucketlist.domain

import org.rekotlin.Action
import pt.josemssilva.bucketlist.domain.list.ItemsReducer

fun appReducer(action: Action, state: AppState?): AppState {
    val currentState = state ?: AppState()

    return AppState(
        items = ItemsReducer.reduce(action, currentState.items)
    )
}