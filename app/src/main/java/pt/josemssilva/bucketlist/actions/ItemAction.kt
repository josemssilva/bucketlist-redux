package pt.josemssilva.bucketlist.actions

import org.rekotlin.Action
import pt.josemssilva.bucketlist.entities.Item

sealed class ItemAction : Action {
    object Refresh : ItemAction()
    data class ItemsLoaded(val items: List<Item>) : ItemAction()
    object CreateItem : ItemAction()
    data class ItemCreated(val item: Item) : ItemAction()
    data class EditItem(val item: Item) : ItemAction()
    data class ItemEdited(val item: Item) : ItemAction()
    data class DeleteItem(val item: Item) : ItemAction()
    data class ItemDeleted(val item: Item) : ItemAction()
}