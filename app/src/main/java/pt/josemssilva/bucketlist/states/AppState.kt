package pt.josemssilva.bucketlist.states

import org.rekotlin.StateType

data class AppState(
    val navigation: NavigationState = NavigationState(),
    val items: ItemsState = ItemsState(),
    val session: SessionState = SessionState()
) : StateType