package pt.josemssilva.bucketlist.data.entities

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val id: String = "",
    val description: String = "",
    val quantity: Quantity = Quantity()
) : Parcelable {
    enum class Keys(val value: String) {
        ID("id"),
        DESCRIPTION("description")
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readParcelable<Quantity>(Quantity::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(description)
        writeParcelable(quantity, 0)
    }

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

        @JvmField
        val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {
            override fun createFromParcel(source: Parcel): Item = Item(source)
            override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
        }
    }
}

fun Item.toMap() = HashMap<String, String>().apply {
    put(Item.Keys.ID.value, id)
    put(Item.Keys.DESCRIPTION.value, description)
    put(Quantity.Keys.VALUE.value, quantity.value.toString())
    put(Quantity.Keys.UNIT.value, quantity.unit.name)
}