package pt.josemssilva.bucketlist.domain

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.domain.list.ItemsState

data class AppState(
    val items: ItemsState = ItemsState()
) : StateType