package pt.josemssilva.bucketlist.modules.items

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.MainActivity
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.store

class ListFragment : Fragment(), StoreSubscriber<AppState> {

    interface RouteListener {
        fun createItem()
        fun editItem(item: Item)
    }

    private var listener: RouteListener? = null

    companion object {
        fun newInstance(listener: RouteListener? = null): Fragment {
            val f = ListFragment()
            f.listener = listener
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            listener?.createItem()
        }

        (activity as MainActivity).supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onStart() {
        super.onStart()

        store().subscribe(this)
    }

    override fun onStop() {
        store().unsubscribe(this)

        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        store().dispatch(ItemsAction.Refresh)
    }

    override fun newState(state: AppState) {
        state.items?.items?.let {
            if (list.adapter == null) {
                list.apply {
                    adapter = ListAdapter(
                        itemClick = ::itemClick,
                        longItemClick = ::itemLongClick
                    )
                    layoutManager = LinearLayoutManager(this@ListFragment.requireContext())
                }
            }
            (list.adapter as ListAdapter).items = it
        }
    }

    private fun showDeleteItemConfirmDialog(item: Item) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_item_title))
            .setMessage(getString(R.string.delete_item_message, item.description))
            .setPositiveButton(R.string.common_confirm) { dialog, _ ->
                store().dispatch(ItemsAction.DeleteItem(item))
                dialog.dismiss()
            }
            .setNegativeButton(R.string.common_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun itemClick(item: Item) {
        listener?.editItem(item)
    }

    private fun itemLongClick(item: Item) {
        showDeleteItemConfirmDialog(item)
    }
}