package pt.josemssilva.bucketlist.domain.list

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

object ItemsReducer {

    fun reduce(action: Action, state: ItemsState) = when (action) {
        is ItemsAction.ItemsLoaded -> itemsLoaded(action.items, state)
        is ItemsAction.ItemCreated -> itemCreated(action.item, state)
        is ItemsAction.ItemEdited -> itemEdited(action.item, state)
        is ItemsAction.ItemDeleted -> itemDeleted(action.item, state)
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