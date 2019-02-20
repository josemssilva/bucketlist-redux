package pt.josemssilva.bucketlist.common.middleware

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import org.rekotlin.ReKotlinInit
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.data.repository.ItemsRepository
import pt.josemssilva.bucketlist.modules.editable.EditableAction
import pt.josemssilva.bucketlist.modules.items.ItemsAction

class APIMiddleware(
    private val itemsRepository: ItemsRepository
) {

    val apiMiddleware: Middleware<AppState> = { dispatch, state ->
        { next ->
            { action ->
                when (action) {
                    is ReKotlinInit -> refreshItemListData(dispatch)
                    is ItemsAction.Refresh -> refreshItemListData(dispatch)
                    is EditableAction.CreateItem -> createItem(action.item, dispatch)
                    is EditableAction.EditItem -> updateItem(action.item, dispatch)
                    is ItemsAction.DeleteItem -> deleteItem(action.item, dispatch)
                }
                next(action)
            }
        }
    }

    private fun refreshItemListData(dispatch: DispatchFunction) {
        itemsRepository.apply {
            GlobalScope.launch(Dispatchers.Main) {
                val list = loadItems()
                dispatch(ItemsAction.ItemsLoaded(list))
            }
        }
    }

    private fun createItem(item: Item, dispatch: DispatchFunction) {
        itemsRepository.apply {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val createdItem = createItem(item)
                    dispatch(EditableAction.ItemCreated(createdItem))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateItem(item: Item, dispatch: DispatchFunction) {
        itemsRepository.apply {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val updatedItem = updateItem(item)
                    dispatch(EditableAction.ItemEdited(updatedItem))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun deleteItem(item: Item, dispatch: DispatchFunction) {
        itemsRepository.apply {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    deleteItem(item)
                    dispatch(ItemsAction.ItemDeleted(item))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}