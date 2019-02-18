package pt.josemssilva.bucketlist.modules.items

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.data.entities.Item

data class ItemsState(
    val items: List<Item> = emptyList()
) : StateType