package pt.josemssilva.bucketlist.modules.editable

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.data.entities.Item

data class EditableState(
    val item: Item = Item(),
    val descriptionError: String? = null,
    val quantityError: String? = null
) : StateType