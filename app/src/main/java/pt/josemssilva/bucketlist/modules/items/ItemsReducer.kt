package pt.josemssilva.bucketlist.modules.items

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.modules.editable.EditableAction

object ItemsReducer {

    fun reduce(action: Action, state: ItemsState?) = when (action) {
        is ItemsAction.ItemsLoaded -> itemsLoaded(
            action.items,
            state
        )
        is ItemsAction.ItemDeleted -> itemDeleted(
            action.item,
            state
        )
        is EditableAction.ItemCreated -> itemCreated(action.item, state)
        is EditableAction.ItemEdited -> itemEdited(action.item, state)
        else -> state
    }

    private fun itemsLoaded(items: List<Item>, state: ItemsState?) = (state ?: ItemsState()).copy(
        items = items
    )

    private fun itemDeleted(item: Item, state: ItemsState?) = (state ?: ItemsState()).let {
        it.copy(
            items = it.items
                .filter { filtered ->
                    filtered != item
                }
        )
    }

    private fun itemCreated(item: Item, state: ItemsState?) = (state ?: ItemsState()).let {
        it.copy(
            items = it.items.plus(item)
        )
    }

    private fun itemEdited(item: Item, state: ItemsState?) = (state ?: ItemsState()).let {
        it.copy(
            items = it.items.map { mapped ->
                if (mapped.id == item.id) item else mapped
            }
        )
    }
}