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
import pt.josemssilva.bucketlist.data.repository.UserRepository
import pt.josemssilva.bucketlist.modules.auth.AuthAction
import pt.josemssilva.bucketlist.modules.editable.EditableAction
import pt.josemssilva.bucketlist.modules.items.ItemsAction

class APIMiddleware(
    private val itemsRepository: ItemsRepository,
    private val userRepository: UserRepository
) {

    val apiMiddleware: Middleware<AppState> = { dispatch, state ->
        { next ->
            { action ->
                when (action) {
                    is ReKotlinInit -> checkUserAuthentication(dispatch)
                    is AuthAction.Login -> performLogin(action.username, action.password, dispatch)
                    is AuthAction.Logout -> performLogout()
                    is ItemsAction.Refresh -> refreshItemListData(dispatch)
                    is EditableAction.Editing.Submit -> submitItem(action.item, dispatch)
                    is ItemsAction.DeleteItem -> deleteItem(action.item, dispatch)
                }
                next(action)
            }
        }
    }

    private fun checkUserAuthentication(dispatch: DispatchFunction) {
        userRepository.apply {
            GlobalScope.launch(Dispatchers.Main) {
                val user = loggedUser()
                user?.apply {
                    dispatch(AuthAction.LoginSuccess(user))
                } ?: dispatch(AuthAction.Logout)
            }
        }
    }

    private fun performLogin(username: String, password: String, dispatch: DispatchFunction) {
        userRepository.apply {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val user = performLogin(username, password)
                    dispatch(AuthAction.LoginSuccess(user))
                } catch (e: Exception) {
                    dispatch(AuthAction.LoginError(e.localizedMessage))
                }
            }
        }
    }

    private fun performLogout() {
        userRepository.apply {
            GlobalScope.launch {
                performLogout()
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

    private fun submitItem(item: Item, dispatch: DispatchFunction) {
        if (item.id.isBlank()) createItem(item, dispatch)
        else updateItem(item, dispatch)
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