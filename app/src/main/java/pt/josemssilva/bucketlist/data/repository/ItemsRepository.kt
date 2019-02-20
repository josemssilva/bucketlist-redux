package pt.josemssilva.bucketlist.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import pt.josemssilva.bucketlist.data.entities.Item
import pt.josemssilva.bucketlist.data.entities.Quantity
import pt.josemssilva.bucketlist.data.entities.QuantityUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ItemsRepository(
    private val firestoreInstance: FirebaseFirestore
) {

    suspend fun loadItems(): List<Item> {
        return suspendCoroutine { continuation ->
            firestoreInstance.collection("bucketlist")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = task.result?.documents?.map { doc ->
                            mapFrom(doc)
                        }?.toList() ?: emptyList()

                        continuation.resume(list)
                    } else {
                        continuation.resumeWithException(Exception("Something to be treated later"))
                    }
                }
        }
    }

    suspend fun createItem(item: Item): Item {
        return suspendCoroutine { continuation ->
            firestoreInstance.collection("bucketlist")
                .add(item.toMap())
                .addOnSuccessListener { doc ->
                    doc.id.let {
                        continuation.resume(
                            item.copy(id = it)
                        )
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(Exception("Something to be treated later"))
                }
        }
    }

    suspend fun updateItem(item: Item): Item {
        return suspendCoroutine { continuation ->
            firestoreInstance.collection("bucketlist")
                .document(item.id)
                .set(item.toMap())
                .addOnSuccessListener {
                    continuation.resume(item)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(Exception("Something to be treated later"))
                }
        }
    }

    suspend fun deleteItem(item: Item): Item {
        return suspendCoroutine { continuation ->
            firestoreInstance.collection("bucketlist")
                .document(item.id)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(item)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(Exception("Something to be treated later"))
                }
        }
    }

}

fun mapFrom(doc: DocumentSnapshot) = Item(
    doc.id,
    doc[Item.Keys.DESCRIPTION.value] as String? ?: "",
    Quantity(
        (doc[Quantity.Keys.VALUE.value] as String? ?: "0").toInt(),
        QuantityUnit.UNIT
    )
)

fun Item.toMap() = HashMap<String, String>().apply {
    put(Item.Keys.DESCRIPTION.value, description)
    put(Quantity.Keys.VALUE.value, quantity.value.toString())
    put(Quantity.Keys.UNIT.value, quantity.unit.name)
}