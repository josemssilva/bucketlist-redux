package pt.josemssilva.bucketlist.middleware

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import pt.josemssilva.bucketlist.ItemsRepository
import pt.josemssilva.bucketlist.actions.ItemAction
import pt.josemssilva.bucketlist.states.AppState

val apiMiddleware: Middleware<AppState> = { dispatch, state ->
    { next ->
        { action ->
            when (action) {
                is ItemAction.Refresh -> refreshItemListData(dispatch)
            }
            next(action)
        }
    }
}

private fun refreshItemListData(dispatch: DispatchFunction) {
    ItemsRepository(FirebaseFirestore.getInstance()).apply {
        GlobalScope.launch {
            val list = loadItems()
            dispatch(ItemAction.ItemsLoaded(list))
        }
    }
}