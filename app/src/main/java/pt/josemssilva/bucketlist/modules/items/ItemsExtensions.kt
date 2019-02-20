package pt.josemssilva.bucketlist.modules.items

import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.data.entities.QuantityUnit

fun QuantityUnit.textResource() = when (this) {
    QuantityUnit.KILOGRAM -> R.string.quantity_unit_kilogram
    QuantityUnit.GRAM -> R.string.quantity_unit_gram
    QuantityUnit.LITER -> R.string.quantity_unit_liter
    QuantityUnit.UNIT -> R.string.quantity_unit_unit
}