package pt.josemssilva.bucketlist.modules.editable

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

object EditableReducer {
    fun reduce(action: Action, state: EditableState?) = when (action) {

        is EditableAction.ItemCreated -> itemCreated(
            action.item,
            state
        )
        is EditableAction.ItemEdited -> itemEdited(
            action.item,
            state
        )

        else -> state
    }

    private fun itemCreated(item: Item, state: EditableState?) = null

    private fun itemEdited(item: Item, state: EditableState?) = null
}