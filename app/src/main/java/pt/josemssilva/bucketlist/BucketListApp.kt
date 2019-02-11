package pt.josemssilva.bucketlist

import android.app.Application
import org.rekotlin.Store
import pt.josemssilva.bucketlist.middleware.apiMiddleware
import pt.josemssilva.bucketlist.reducers.appReducer

class BucketListApp : Application() {
    val store = Store(
        reducer = ::appReducer,
        state = null,
        middleware = listOf(apiMiddleware)
    )
}