package pt.josemssilva.bucketlist.reducers

import org.rekotlin.Action
import pt.josemssilva.bucketlist.actions.ItemAction
import pt.josemssilva.bucketlist.entities.Item
import pt.josemssilva.bucketlist.states.ItemsState

object ItemReducer {

    fun reduce(action: Action, state: ItemsState) = when (action) {
        is ItemAction.ItemsLoaded -> itemsLoaded(action.items, state)
        is ItemAction.ItemCreated -> itemCreated(action.item, state)
        is ItemAction.ItemEdited -> itemEdited(action.item, state)
        is ItemAction.ItemDeleted -> itemDeleted(action.item, state)
        else -> state
    }

    private fun itemsLoaded(items: List<Item>, state: ItemsState) = state.copy(
        items = items
    )

    private fun itemCreated(item: Item, state: ItemsState) = state.copy(
        items = state.items
            .filter {
                it != item
            }
            .asIterable()
            .plus(item)
    )

    private fun itemEdited(item: Item, state: ItemsState) = state.copy(
        items = state.items
            .map {
                if (it.id == item.id) item else it
            }
    )

    private fun itemDeleted(item: Item, state: ItemsState) = state.copy(
        items = state.items
            .filter {
                it != item
            }
    )
}