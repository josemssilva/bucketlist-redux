package pt.josemssilva.bucketlist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.modules.auth.AuthFragment
import pt.josemssilva.bucketlist.modules.auth.LoginState
import pt.josemssilva.bucketlist.modules.editable.EditItemFragment
import pt.josemssilva.bucketlist.modules.items.ListFragment

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    private var currentState: AppState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        store().subscribe(this)
    }

    override fun onStop() {
        store().unsubscribe(this)

        super.onStop()
    }

    override fun newState(state: AppState) {
        if (state.auth != currentState?.auth) {
            navigate(
                if (state.auth?.loginState == LoginState.ACTIVE) ListFragment.newInstance(object :
                    ListFragment.RouteListener {
                    override fun createItem() {
                        navigate(EditItemFragment.newInstance())
                    }

                    override fun editItem(item: Item) {
                        navigate(EditItemFragment.newInstance(item))
                    }
                })
                else AuthFragment.newInstance(),
                false
            )
        }

        currentState = state
    }

    private fun navigate(fragment: Fragment, addToBackStack: Boolean = true) {
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
}