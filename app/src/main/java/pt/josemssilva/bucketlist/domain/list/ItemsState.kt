package pt.josemssilva.bucketlist.domain.list

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.data.entities.Item

data class ItemsState(
    val items: List<Item> = emptyList()
) : StateType