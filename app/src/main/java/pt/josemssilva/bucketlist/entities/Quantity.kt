package pt.josemssilva.bucketlist.entities

data class Quantity(
    val value: Int = 0,
    val unit: QuantityUnit = QuantityUnit.KILOGRAM
) {
    enum class Keys(val value: String) {
        VALUE("value"),
        UNIT("unit")
    }
}

enum class QuantityUnit {
    LITER,
    GRAM,
    UNIT,
    KILOGRAM
}