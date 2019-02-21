package pt.josemssilva.bucketlist.modules.editable

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.common_loading.*
import kotlinx.android.synthetic.main.editable_fragment.*
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.MainActivity
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.data.entities.Quantity
import pt.josemssilva.bucketlist.data.entities.QuantityUnit
import pt.josemssilva.bucketlist.store

private const val BUNDLE_ITEM = "bundle_item"

class EditItemFragment : Fragment(), StoreSubscriber<AppState> {

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

    lateinit var editableItem: Item

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.editable_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_button.setOnClickListener {

            val item = editableItem.copy(
                description = description.text.toString(),
                quantity = Quantity(
                    value = quantity.text.toString().toInt(),
                    unit = QuantityUnit.fromValue(unit_spinner.selectedItem as String)
                )
            )

            store().dispatch(EditableAction.Editing.Submit(item))
        }

        unit_spinner.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            QuantityUnit.values().map {
                it.name
            }
        )

        (activity as MainActivity).supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        editableItem = arguments?.getParcelable(BUNDLE_ITEM) ?: Item()

        store().dispatch(EditableAction.Editing.Data(editableItem))
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
        state.editable?.apply {
            redrawUI(this)
        } ?: requireActivity().onBackPressed()
    }

    private fun redrawUI(state: EditableState) {
        editableItem = state.item
        toggleLoading(state.stateType == EditableStateType.SUBMITTING)

        refreshInputFields(editableItem)

        state.genericError?.apply {
            AlertDialog.Builder(requireContext())
                .setMessage(this)
                .setPositiveButton(R.string.common_ok) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        description.error = state.descriptionError
        quantity.error = state.quantityError
    }

    private fun refreshInputFields(item: Item) {

        description.setText(item.description)
        quantity.setText("${item.quantity.value}")

        unit_spinner.setSelection(item.quantity.unit.ordinal)
    }

    private fun toggleLoading(isVisible: Boolean) {
        loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}