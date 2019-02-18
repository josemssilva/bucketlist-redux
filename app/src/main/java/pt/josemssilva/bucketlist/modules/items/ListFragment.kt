package pt.josemssilva.bucketlist.modules.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.store

class ListFragment : Fragment(), StoreSubscriber<AppState>, ListAdapter.Listener {

    companion object {
        fun newInstance(): Fragment {
            return ListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            activity?.let {
                store().dispatch(ItemsAction.CreateItem)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        store().subscribe(this)
    }

    override fun onPause() {
        store().unsubscribe(this)

        super.onPause()
    }

    override fun newState(state: AppState) {
        state.items.items.let {
            if (list.adapter == null) {
                list.apply {
                    adapter = ListAdapter(this@ListFragment)
                    layoutManager = LinearLayoutManager(this@ListFragment.requireContext())
                }
            }
            (list.adapter as ListAdapter).items = it
        }
    }

    override fun itemClicked(item: Item) {
        store().dispatch(ItemsAction.EditItem(item))
    }
}