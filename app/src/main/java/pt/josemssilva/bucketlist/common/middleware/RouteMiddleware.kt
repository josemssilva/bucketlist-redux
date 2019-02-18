package pt.josemssilva.bucketlist.common.middleware

import org.rekotlin.Middleware
import org.rekotlin.ReKotlinInit
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.modules.items.ItemsAction

sealed class Route {
    object Back : Route()
}

sealed class ItemsRoute : Route() {
    object All : ItemsRoute()
    object Create : ItemsRoute()
    data class Edit(val item: Item) : ItemsRoute()
    data class Delete(val item: Item) : ItemsRoute()
}

interface RouteListener {
    fun navigateTo(route: Route)
}

class RouteMiddleware(
    listener: RouteListener
) {
    val intercept: Middleware<AppState> = { dispatch, state ->
        { next ->
            { action ->

                when (action) {
                    is ReKotlinInit -> listener.navigateTo(ItemsRoute.All)
                    is ItemsAction.Start -> listener.navigateTo(ItemsRoute.All)
                    is ItemsAction.CreateItem -> listener.navigateTo(ItemsRoute.Create)
                    is ItemsAction.EditItem -> listener.navigateTo(ItemsRoute.Edit(action.item))
                    is ItemsAction.DeleteItem -> listener.navigateTo(ItemsRoute.Delete(action.item))
                }
                next(action)
            }
        }
    }
}