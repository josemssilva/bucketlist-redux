package pt.josemssilva.bucketlist.modules.editable

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.data.entities.Item

data class EditableState(
    val item: Item = Item(),
    val genericError: String? = null,
    val descriptionError: String? = null,
    val quantityError: String? = null,
    val stateType: EditableStateType = EditableStateType.EDITING
) : StateType

enum class EditableStateType {
    EDITING,
    SUBMITTING
}