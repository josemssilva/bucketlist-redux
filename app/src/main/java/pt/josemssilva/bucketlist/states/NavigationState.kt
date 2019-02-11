package pt.josemssilva.bucketlist.states

import org.rekotlin.StateType
import pt.josemssilva.bucketlist.entities.Item

data class NavigationState(
    val screen: NavigationScreen = NavigationScreen.ItemsList
) : StateType

sealed class NavigationScreen {
    object Auth : NavigationScreen()
    object ItemsList : NavigationScreen()
    data class Editable(val item: Item) : NavigationScreen()
    sealed class Dialog {
        data class ItemDelete(val item: Item) : NavigationScreen()
    }

    object AppKill : NavigationScreen() // should this be a screen/state ?!
}