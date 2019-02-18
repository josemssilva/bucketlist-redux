package pt.josemssilva.bucketlist.common

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.modules.items.ItemsState

data class AppState(
    val items: ItemsState = ItemsState()
) : StateType