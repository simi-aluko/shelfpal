package com.simi.shelfpal.ui.addEditItem.repository

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.storage.Data
import dev.gitlive.firebase.storage.FirebaseStorage
import dev.gitlive.firebase.storage.storage

class AddItemRepositoryImpl(
    private val shelfPalItemsDao: ShelfPalItemsDao,
    private val firebaseStorage: FirebaseStorage = Firebase.storage
) : AddItemRepository {

    override suspend fun saveItem(shelfPalItem: ShelfPalItem) {
        shelfPalItemsDao.insert(shelfPalItem)
    }

    override fun getItem(id: Long) = shelfPalItemsDao.getById(id)

    override suspend fun updateItem(shelfPalItem: ShelfPalItem) = shelfPalItemsDao.update(shelfPalItem)

    override suspend fun uploadImage(data: Data, fileName: String): String {
        val userId = Firebase.auth.currentUser?.uid
            ?: run {
                Firebase.auth.signInAnonymously()
                Firebase.auth.currentUser?.uid
            }
            ?: throw IllegalStateException("Authentication failed: unable to obtain user ID")

        val reference = firebaseStorage.reference("images/$userId/$fileName")
        reference.putData(data)
        return reference.getDownloadUrl()
    }
}