package pt.josemssilva.bucketlist.entities

data class Item(
    val id: String = "",
    val description: String = "",
    val quantity: Quantity = Quantity()
) {
    companion object {
        val EMPTY = Item()

        fun fromMap(map: Map<String, String>) = Item(
            map[Item.Keys.ID.value] ?: "",
            map[Item.Keys.DESCRIPTION.value] ?: "",
            Quantity(
                map[Quantity.Keys.VALUE.value]?.toInt() ?: 0,
                QuantityUnit.valueOf(
                    map[Quantity.Keys.UNIT.value] ?: QuantityUnit.UNIT.name
                )
            )

        )
    }

    enum class Keys(val value: String) {
        ID("id"),
        DESCRIPTION("description")
    }
}

fun Item.toMap() = HashMap<String, String>().apply {
    put(Item.Keys.ID.value, id)
    put(Item.Keys.DESCRIPTION.value, description)
    put(Quantity.Keys.VALUE.value, quantity.value.toString())
    put(Quantity.Keys.UNIT.value, quantity.unit.name)
}