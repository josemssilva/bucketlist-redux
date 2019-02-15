package pt.josemssilva.bucketlist.data.entities

import android.os.Parcel
import android.os.Parcelable

data class Quantity(
    val value: Int = 0,
    val unit: QuantityUnit = QuantityUnit.KILOGRAM
) : Parcelable {
    enum class Keys(val value: String) {
        VALUE("value"),
        UNIT("unit")
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        QuantityUnit.values()[source.readInt()]
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(value)
        writeInt(unit.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Quantity> = object : Parcelable.Creator<Quantity> {
            override fun createFromParcel(source: Parcel): Quantity = Quantity(source)
            override fun newArray(size: Int): Array<Quantity?> = arrayOfNulls(size)
        }
    }
}

enum class QuantityUnit {
    LITER,
    GRAM,
    UNIT,
    KILOGRAM
}