package pt.josemssilva.bucketlist.actions

import org.rekotlin.Action

sealed class NavigationAction : Action {
    object Back : NavigationAction()
}