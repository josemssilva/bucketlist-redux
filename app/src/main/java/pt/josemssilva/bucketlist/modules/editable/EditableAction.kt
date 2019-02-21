package pt.josemssilva.bucketlist.modules.editable

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

sealed class EditableAction : Action {
    sealed class Editing : EditableAction() {
        data class Data(val item: Item) : EditableAction()
        data class Submit(val item: Item) : EditableAction()
        data class Error(val errors: Map<EditingErrorField, String>) : EditableAction()
    }

    data class ItemCreated(val item: Item) : EditableAction()
    data class ItemEdited(val item: Item) : EditableAction()
}

enum class EditingErrorField {
    GENERIC,
    DESCRIPTION,
    QUANTITY
}