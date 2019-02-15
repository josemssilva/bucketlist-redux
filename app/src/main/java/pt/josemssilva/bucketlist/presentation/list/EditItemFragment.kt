package pt.josemssilva.bucketlist.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.editable_fragment.*
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.data.entities.Quantity
import pt.josemssilva.bucketlist.data.entities.QuantityUnit
import pt.josemssilva.bucketlist.domain.list.ItemsAction
import pt.josemssilva.bucketlist.store

private const val BUNDLE_ITEM = "bundle_item"

class EditItemFragment : Fragment() {

    companion object {
        fun newInstance(item: Item? = null): EditItemFragment {
            val f = EditItemFragment()
            item?.let {
                f.arguments = Bundle().apply {
                    putParcelable(BUNDLE_ITEM, it)
                }
            }
            return f
        }
    }

    var editableItem: Item? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.editable_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Item>(BUNDLE_ITEM)?.let {
            editableItem = it
        }

        quantity.setText("0")

        editableItem?.let {
            description.setText(it.description)
            quantity.setText("10")
        }

        save_button.setOnClickListener {

            val item = (editableItem ?: Item()).copy(
                description = description.text.toString(),
                quantity = Quantity(
                    value = quantity.text.toString().toInt(),
                    unit = QuantityUnit.UNIT
                )
            )

            store().dispatch(
                if (item.id.isBlank()) ItemsAction.SubmitNewItem(item) else ItemsAction.SubmitUpdatedItem(item)
            )
        }
    }
}