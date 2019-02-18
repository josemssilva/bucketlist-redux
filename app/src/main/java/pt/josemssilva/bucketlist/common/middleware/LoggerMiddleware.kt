package pt.josemssilva.bucketlist.common.middleware

import android.util.Log
import org.rekotlin.Middleware
import pt.josemssilva.bucketlist.common.AppState


val loggerMiddleware: Middleware<AppState> = { dispatch, state ->
    { next ->
        { action ->
            Log.d("LoggerMiddleware", "action -> $action")
            Log.d("LoggerMiddleware", "state -> ${state()}")

            next(action)
        }
    }
}
