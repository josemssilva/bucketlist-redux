package pt.josemssilva.bucketlist

import android.app.Application
import com.google.firebase.FirebaseApp
import org.rekotlin.Store
import pt.josemssilva.bucketlist.domain.AppState
import pt.josemssilva.bucketlist.domain.appReducer
import pt.josemssilva.bucketlist.domain.middleware.apiMiddleware

fun store() = BucketListApp.store

class BucketListApp : Application() {

    companion object {
        internal lateinit var store: Store<AppState>
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        store = Store(
            reducer = ::appReducer,
            state = null,
            middleware = listOf(
                apiMiddleware
            )
        )
    }
}