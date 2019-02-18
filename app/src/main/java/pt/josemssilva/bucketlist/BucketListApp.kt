package pt.josemssilva.bucketlist

import android.app.Application
import com.google.firebase.FirebaseApp
import org.rekotlin.Store
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.common.appReducer
import pt.josemssilva.bucketlist.common.middleware.*

fun store() = BucketListApp.store

class BucketListApp : Application(), RouteListener {

    companion object {
        internal lateinit var store: Store<AppState>
    }

    private var routeList = mutableListOf<Route>()

    private val routeSubscribers = mutableListOf<RouteListener>()

    fun subscribeToRouteChanges(subscriber: RouteListener) {
        routeSubscribers.remove(subscriber)

        routeSubscribers.add(subscriber)
        if (routeList.isNotEmpty())
            subscriber.navigateTo(routeList.last())
    }

    fun unsubscribeToRouteChanges(subscriber: RouteListener) {
        routeSubscribers.remove(subscriber)
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        val routeMiddleware = RouteMiddleware(this)

        store = Store(
            reducer = ::appReducer,
            state = null,
            middleware = listOf(
                loggerMiddleware,
                apiMiddleware,
                routeMiddleware.intercept
            )
        )
    }

    override fun navigateTo(route: Route) {
        val routeIndex = routeList.indexOfFirst { it == route }

        if (routeIndex != -1) {
            routeList = routeList.filterIndexed { index, _ -> index < routeIndex }.toMutableList()
        }

        (routeList as ArrayList).add(route)

        routeSubscribers.forEach {
            it.navigateTo(route)
        }
    }
}