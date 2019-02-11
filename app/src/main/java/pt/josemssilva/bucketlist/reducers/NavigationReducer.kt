package pt.josemssilva.bucketlist.reducers

import org.rekotlin.Action
import pt.josemssilva.bucketlist.actions.ItemAction
import pt.josemssilva.bucketlist.actions.NavigationAction
import pt.josemssilva.bucketlist.entities.Item
import pt.josemssilva.bucketlist.states.NavigationScreen
import pt.josemssilva.bucketlist.states.NavigationState

object NavigationReducer {
    fun reduce(action: Action, state: NavigationState) = when (action) {
        is ItemAction.CreateItem -> navigateToCreateItem(state)
        is ItemAction.EditItem -> navigateToEditItem(action.item, state)
        is ItemAction.DeleteItem -> navigateToDeleteItem(action.item, state)
        is NavigationAction.Back -> navigateBack(state)
        else -> state
    }

    private fun navigateToCreateItem(state: NavigationState) = state.copy(
        screen = NavigationScreen.Editable(Item())
    )

    private fun navigateToEditItem(item: Item, state: NavigationState) = state.copy(
        screen = NavigationScreen.Editable(item)
    )

    private fun navigateToDeleteItem(item: Item, state: NavigationState) = state.copy(
        screen = NavigationScreen.Dialog.ItemDelete(item)
    )

    private fun navigateBack(state: NavigationState): NavigationState {
        return when (state.screen) {
            is NavigationScreen.Editable,
            is NavigationScreen.Dialog.ItemDelete -> state.copy(screen = NavigationScreen.ItemsList)

            else -> state.copy(screen = NavigationScreen.AppKill)
        }
    }
}