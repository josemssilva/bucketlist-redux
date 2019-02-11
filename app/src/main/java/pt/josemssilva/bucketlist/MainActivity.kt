package pt.josemssilva.bucketlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.actions.ItemAction
import pt.josemssilva.bucketlist.states.AppState

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        (application as BucketListApp).store.subscribe(this)
        (application as BucketListApp).store.dispatch(ItemAction.Refresh)
    }

    override fun onStop() {
        (application as BucketListApp).store.unsubscribe(this)

        super.onStop()
    }

    override fun newState(state: AppState) {
        runOnUiThread {
            if (list.adapter == null) {
                list.adapter = ListAdapter()
                list.layoutManager = LinearLayoutManager(this)
            }

            (list.adapter as ListAdapter).items = state.items.items
        }
    }
}