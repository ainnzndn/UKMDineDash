import android.os.Parcel
import android.os.Parcelable

data class EachStallMenuItem(val name: String?, val price: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EachStallMenuItem> {
        override fun createFromParcel(parcel: Parcel): EachStallMenuItem {
            return EachStallMenuItem(parcel)
        }

        override fun newArray(size: Int): Array<EachStallMenuItem?> {
            return arrayOfNulls(size)
        }
    }
}

val eachStallMenuItemsMap = mapOf(
    "Lot 1: Nasi Ayam" to listOf(
        EachStallMenuItem("Nasi Ayam Biasa", "RM4.50"),
        EachStallMenuItem("Nasi Ayam Peha", "RM5.50"),
        EachStallMenuItem("Nasi Ayam Hainan", "RM4.50"),
        EachStallMenuItem("Nasi Ayam Madu", "RM5.50"),
        EachStallMenuItem("Nasi Ayam Special", "RM6.50")
    ),
    "Lot 2: Nasi Ayam Penyet" to listOf(
        EachStallMenuItem("Nasi Ayam Penyet", "RM5.90"),
        EachStallMenuItem("Nasi Ikan Keli Penyet", "RM6.90"),
        EachStallMenuItem("Nasi Talapia Penyet", "RM7.90"),
        EachStallMenuItem("Nasi Kukus Biasa", "RM5.90"),
        EachStallMenuItem("Nasi Kukus Ayam Dara", "RM6.90")
    ),
    "Lot 3: Masakan Thai" to listOf(
        EachStallMenuItem("Tomyam/Paprik/Beraneka", "RM8.50"),
        EachStallMenuItem("Nasi Putih Berlauk", "RM8.00"),
        EachStallMenuItem("Nasi Goreng/Beraneka", "RM7.00"),
        EachStallMenuItem("Mee Goreng/Beraneka", "RM6.50"),
        EachStallMenuItem("Mihun Goreng/Beraneka", "RM6.50"),
        EachStallMenuItem("Kueyteow Goreng/Beraneka", "RM6.50")
    ),
    "Lot 4: Western" to listOf(
        EachStallMenuItem("Pasta/Spaghetti", "RM6.50"),
        EachStallMenuItem("Chicken/Beef Lasagna", "RM6.50"),
        EachStallMenuItem("Chicken Chop", "RM7.00"),
        EachStallMenuItem("Beef Steak", "RM8.00"),
        EachStallMenuItem("Lamb Chop", "RM8.50"),
        EachStallMenuItem("Fish n Chips", "RM7.50")
    ),
    "Lot 5: Air Gantung" to listOf(
        EachStallMenuItem("Sirap/Teh O/Kopi O", "RM1.50"),
        EachStallMenuItem("Teh/Kopi/Bandung", "RM2.00"),
        EachStallMenuItem("Milo/Nescafe/Greentea", "RM2.50"),
        EachStallMenuItem("Jus Buah", "RM3.00"),
        EachStallMenuItem("ABC/Cendol", "RM3.50")
    ),
    "Lot 6: BigCup" to listOf(
        EachStallMenuItem("Creamy Strawberry", "RM2.50"),
        EachStallMenuItem("Teh Ais Tarik", "RM2.50"),
        EachStallMenuItem("Milky Honeydew", "RM2.50"),
        EachStallMenuItem("Double Chocolate", "RM2.50"),
        EachStallMenuItem("Kopi Ais Kaww", "RM2.50"),
        EachStallMenuItem("Dreamy Vanilla Blue", "RM2.50")
    )
)
