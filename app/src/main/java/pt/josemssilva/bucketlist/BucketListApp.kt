package pt.josemssilva.bucketlist

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import org.rekotlin.Store
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.common.appReducer
import pt.josemssilva.bucketlist.common.middleware.APIMiddleware
import pt.josemssilva.bucketlist.common.middleware.loggerMiddleware
import pt.josemssilva.bucketlist.data.repository.ItemsRepository

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
                APIMiddleware(ItemsRepository(FirebaseFirestore.getInstance())).apiMiddleware,
                router.intercept
            )
        )
    }
}