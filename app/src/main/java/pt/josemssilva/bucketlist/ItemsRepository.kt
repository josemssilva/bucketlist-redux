package pt.josemssilva.bucketlist

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import pt.josemssilva.bucketlist.entities.Item
import pt.josemssilva.bucketlist.entities.Quantity
import pt.josemssilva.bucketlist.entities.QuantityUnit
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
                            mapFrom(doc) ?: Item()
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
                .add(item)
                .addOnSuccessListener { doc ->
                    doc.get().result?.let {
                        continuation.resume(
                            mapFrom(it) ?: Item()
                        )
                    } ?: continuation.resumeWithException(Exception("Something to be treated later"))
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
                .set(item)
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
    doc["id"] as String? ?: "",
    doc["description"] as String? ?: "",
    Quantity(
        (doc["quantity"] as String? ?: "0").toInt(),
        QuantityUnit.UNIT
    )
)

//fun mapFrom(doc: DocumentSnapshot) = doc.toObject(Item::class.java)