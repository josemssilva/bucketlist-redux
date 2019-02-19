package pt.josemssilva.bucketlist

import android.app.Application
import com.google.firebase.FirebaseApp
import org.rekotlin.Store
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.common.appReducer
import pt.josemssilva.bucketlist.common.middleware.apiMiddleware
import pt.josemssilva.bucketlist.common.middleware.loggerMiddleware

fun store() = BucketListApp.store
fun router() = BucketListApp.router

class BucketListApp : Application() {

    companion object {
        internal lateinit var store: Store<AppState>
        internal val router = Router()
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        store = Store(
            reducer = ::appReducer,
            state = null,
            middleware = listOf(
                loggerMiddleware,
                apiMiddleware,
                router.intercept
            )
        )
    }
}