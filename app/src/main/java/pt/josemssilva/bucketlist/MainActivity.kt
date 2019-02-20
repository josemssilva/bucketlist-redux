package pt.josemssilva.bucketlist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.modules.auth.AuthFragment
import pt.josemssilva.bucketlist.modules.editable.EditItemFragment
import pt.josemssilva.bucketlist.modules.items.ListFragment

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState>, RouteListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        store().subscribe(this)
        router().subscribe(this)
    }

    override fun onStop() {
        store().unsubscribe(this)
        router().unsubscribe(this)

        super.onStop()
    }

    override fun newState(state: AppState) {

    }

    override fun navigateTo(route: Route) {
        when (route) {
            is AuthRoute.Main -> navigate(AuthFragment.newInstance(), false)
            is ItemsRoute.All -> navigate(ListFragment.newInstance(), false)
            is ItemsRoute.Create -> navigate(EditItemFragment.newInstance())
            is ItemsRoute.Edit -> navigate(EditItemFragment.newInstance(route.item))
            is Route.Finish -> finish()
        }
    }

    fun navigate(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackStack)
            transaction.addToBackStack(fragment.tag)

        transaction.commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        router().goBack()
    }
}