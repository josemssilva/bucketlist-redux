package pt.josemssilva.bucketlist

import org.rekotlin.Middleware
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.modules.auth.AuthAction
import pt.josemssilva.bucketlist.modules.editable.EditableAction
import pt.josemssilva.bucketlist.modules.items.ItemsAction
import java.util.*

sealed class Route {
    object Finish : Route()
}

sealed class ItemsRoute : Route() {
    object All : ItemsRoute()
    object Create : ItemsRoute()
    data class Edit(val item: Item) : ItemsRoute()
    data class Delete(val item: Item) : ItemsRoute()
}

sealed class AuthRoute : Route() {
    object Main : AuthRoute()
}

interface RouteListener {
    fun navigateTo(route: Route)
}

class Router {

    private var routeList = mutableListOf<Route>()

    private val routeSubscribers = mutableListOf<RouteListener>()

    fun subscribe(subscriber: RouteListener) {
        routeSubscribers.remove(subscriber)

        routeSubscribers.add(subscriber)
        if (routeList.isNotEmpty())
            subscriber.navigateTo(routeList.last())
    }

    fun unsubscribe(subscriber: RouteListener) {
        routeSubscribers.remove(subscriber)
    }

    fun navigateTo(route: Route) {
        if (route !is Route.Finish) {
            val routeIndex = routeList.indexOfFirst { it == route }

            if (routeIndex != -1) {
                routeList = routeList.filterIndexed { index, _ -> index < routeIndex }.toMutableList()
            }

            (routeList as ArrayList).add(route)
        }

        routeSubscribers.forEach {
            it.navigateTo(route)
        }
    }

    fun goBack() {
        if (routeList.size > 1) {
            routeList.removeAt(routeList.size - 1)
            navigateTo(routeList.last())
        } else {
            navigateTo(Route.Finish)
        }
    }

    val intercept: Middleware<AppState> = { dispatch, state ->
        { next ->
            { action ->

                when (action) {
                    is AuthAction.LoginSuccess -> navigateTo(ItemsRoute.All)
                    is AuthAction.Logout -> navigateTo(AuthRoute.Main)
                    is ItemsAction.Start -> navigateTo(ItemsRoute.All)
                    is EditableAction.ItemCreated -> navigateTo(ItemsRoute.All)
                    is EditableAction.ItemEdited -> navigateTo(ItemsRoute.All)
                    is ItemsAction.ItemDeleted -> navigateTo(ItemsRoute.All)
                }
                next(action)
            }
        }
    }
}