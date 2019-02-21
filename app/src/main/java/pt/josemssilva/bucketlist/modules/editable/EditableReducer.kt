package pt.josemssilva.bucketlist.modules.editable

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

object EditableReducer {
    fun reduce(action: Action, state: EditableState?) = when (action) {

        is EditableAction.Editing.Data -> editingItem(action.item, state)
        is EditableAction.Editing.Submit -> submitted(action.item, state)
        is EditableAction.Editing.Error -> submissionFailed(action.errors, state)

        is EditableAction.ItemCreated -> itemCreated(action.item, state)
        is EditableAction.ItemEdited -> itemEdited(action.item, state)

        else -> state
    }

    private fun editingItem(item: Item, state: EditableState?): EditableState {
        val currentState = (state ?: EditableState())
        return currentState.copy(
            item = item,
            genericError = null,
            descriptionError = null,
            quantityError = null,
            stateType = EditableStateType.EDITING
        )
    }

    private fun submissionFailed(errors: Map<EditingErrorField, String>, state: EditableState?): EditableState {
        val currentState = (state ?: EditableState())
        return currentState.copy(
            descriptionError = errors[EditingErrorField.DESCRIPTION],
            quantityError = errors[EditingErrorField.QUANTITY],
            genericError = errors[EditingErrorField.GENERIC],
            stateType = EditableStateType.EDITING
        )
    }

    private fun submitted(item: Item, state: EditableState?): EditableState {
        val currentState = state ?: EditableState()
        return currentState.copy(
            item = item,
            genericError = null,
            descriptionError = null,
            quantityError = null,
            stateType = EditableStateType.SUBMITTING
        )
    }

    private fun itemCreated(item: Item, state: EditableState?) = null

    private fun itemEdited(item: Item, state: EditableState?) = null
}