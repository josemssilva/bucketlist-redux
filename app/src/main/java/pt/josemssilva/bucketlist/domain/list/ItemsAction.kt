package pt.josemssilva.bucketlist.domain.list

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

sealed class ItemsAction : Action {
    object Refresh : ItemsAction()
    data class ItemsLoaded(val items: List<Item>) : ItemsAction()
    object CreateItem : ItemsAction()
    data class SubmitNewItem(val item: Item) : ItemsAction()
    data class ItemCreated(val item: Item) : ItemsAction()
    data class EditItem(val item: Item) : ItemsAction()
    data class SubmitUpdatedItem(val item: Item) : ItemsAction()
    data class ItemEdited(val item: Item) : ItemsAction()
    data class DeleteItem(val item: Item) : ItemsAction()
    data class ItemDeleted(val item: Item) : ItemsAction()
}