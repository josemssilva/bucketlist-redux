package pt.josemssilva.bucketlist.modules.items

import org.rekotlin.Action
import pt.josemssilva.bucketlist.data.entities.Item

sealed class ItemsAction : Action {
    object Start : ItemsAction()
    object Refresh : ItemsAction()
    data class ItemsLoaded(val items: List<Item>) : ItemsAction()
    data class DeleteItem(val item: Item) : ItemsAction()
    data class ItemDeleted(val item: Item) : ItemsAction()
}