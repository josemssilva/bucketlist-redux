package pt.josemssilva.bucketlist.modules.editable

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

sealed class EditableAction : Action {
    data class CreateItem(val item: Item) : EditableAction()
    data class ItemCreated(val item: Item) : EditableAction()
    data class EditItem(val item: Item) : EditableAction()
    data class ItemEdited(val item: Item) : EditableAction()
}