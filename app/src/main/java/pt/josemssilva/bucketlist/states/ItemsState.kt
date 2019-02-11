package pt.josemssilva.bucketlist.states

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.entities.Item

data class ItemsState(
    val items: List<Item> = emptyList()
) : StateType