package com.simi.shelfpal.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelfPalItemsDao {
    @Insert
    suspend fun insert(item: ShelfPalItem)

    @Query("SELECT * FROM ShelfPalItem")
    fun getAll(): Flow<List<ShelfPalItem>>

    @Query("SELECT * FROM ShelfPalItem WHERE id = :id")
    fun getById(id: Long): Flow<ShelfPalItem>

    @Query("DELETE FROM ShelfPalItem WHERE id = :id")
    suspend fun deleteById(id: Long)
}