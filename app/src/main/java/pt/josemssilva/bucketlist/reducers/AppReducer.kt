package pt.josemssilva.bucketlist.reducers

import org.rekotlin.Action
import pt.josemssilva.bucketlist.states.AppState

fun appReducer(action: Action, state: AppState?): AppState {
    val currentState = state ?: AppState()

    return AppState(
        navigation = NavigationReducer.reduce(action, currentState.navigation),
        items = ItemReducer.reduce(action, currentState.items)
    )
}