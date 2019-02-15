package pt.josemssilva.bucketlist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.domain.AppState
import pt.josemssilva.bucketlist.presentation.list.ListFragment
import pt.josemssilva.bucketlist.store

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigate(ListFragment.newInstance(), false)
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

    }

    fun navigate(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackStack)
            transaction.addToBackStack(fragment.tag)

        transaction.commitAllowingStateLoss()
    }
}