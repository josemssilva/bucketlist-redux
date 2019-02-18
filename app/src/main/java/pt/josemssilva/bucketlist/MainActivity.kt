package pt.josemssilva.bucketlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.common.middleware.ItemsRoute
import pt.josemssilva.bucketlist.common.middleware.Route
import pt.josemssilva.bucketlist.common.middleware.RouteListener
import pt.josemssilva.bucketlist.modules.items.EditItemFragment
import pt.josemssilva.bucketlist.modules.items.ListFragment

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState>, RouteListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
//        navigate(ListFragment.newInstance(), false)
    }

    override fun onStart() {
        super.onStart()

        store().subscribe(this)
        (application as BucketListApp).subscribeToRouteChanges(this)
    }

    override fun onStop() {
        store().unsubscribe(this)
        (application as BucketListApp).unsubscribeToRouteChanges(this)

        super.onStop()
    }

    override fun newState(state: AppState) {

    }

    override fun navigateTo(route: Route) {
        when (route) {
            is ItemsRoute.All -> navigate(ListFragment.newInstance(), false)
            is ItemsRoute.Create -> navigate(EditItemFragment.newInstance())
            is ItemsRoute.Edit -> navigate(EditItemFragment.newInstance(route.item))
        }
    }

    fun navigate(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackStack)
            transaction.addToBackStack(fragment.tag)

        transaction.commitAllowingStateLoss()
    }
}