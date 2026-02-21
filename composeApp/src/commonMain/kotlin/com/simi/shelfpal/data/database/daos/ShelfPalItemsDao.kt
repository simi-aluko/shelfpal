package com.simi.shelfpal.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelfPalItemsDao {

    @Insert
    suspend fun insert(item: ShelfPalItem)

    @Update
    suspend fun update(item: ShelfPalItem)

    @Delete
    suspend fun deleteById(item: ShelfPalItem)

    @Query("SELECT * FROM ShelfPalItem WHERE id = :id")
    fun getById(id: Long): Flow<ShelfPalItem?>

    @Query(
        """
            SELECT * FROM ShelfPalItem
            WHERE (:category IS NULL OR category = :category)
            AND (:month IS NULL OR expiryMonth = :month)
            AND (:year IS NULL OR expiryYear = :year)
        """
    )
    fun getItems(
        category: String?,
        month: Int?,
        year: Int?
    ): Flow<List<ShelfPalItem>>

    @Query("SELECT * FROM ShelfPalItem")
    suspend fun getItems(): List<ShelfPalItem>
}