package pt.josemssilva.bucketlist.domain.middleware

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import org.rekotlin.ReKotlinInit
import pt.josemssilva.bucketlist.data.repository.ItemsRepository
import pt.josemssilva.bucketlist.domain.AppState
import pt.josemssilva.bucketlist.domain.list.ItemsAction

val apiMiddleware: Middleware<AppState> = { dispatch, state ->
    { next ->
        { action ->
            when (action) {
                is ReKotlinInit -> refreshItemListData(dispatch)
                is ItemsAction.Refresh -> refreshItemListData(dispatch)
            }
            next(action)
        }
    }
}

private fun refreshItemListData(dispatch: DispatchFunction) {
    ItemsRepository(FirebaseFirestore.getInstance()).apply {
        GlobalScope.launch(Dispatchers.Main) {
            val list = loadItems()
            dispatch(ItemsAction.ItemsLoaded(list))
        }
    }
}