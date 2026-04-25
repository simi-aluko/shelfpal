package com.simi.shelfpal.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.database.shelfItemsTable
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelfPalItemsDao {

    @Insert
    suspend fun insert(item: ShelfPalItem)

    @Update
    suspend fun update(item: ShelfPalItem)

    @Delete
    suspend fun deleteById(item: ShelfPalItem)

    @Query("UPDATE $shelfItemsTable SET used = 1 WHERE id = :id")
    suspend fun markAsUsed(id: Long)

    @Query("UPDATE $shelfItemsTable SET used = 1 WHERE id IN (:id)")
    suspend fun markAsUsed(id: List<Long>)

    /**
     *  START REGION: GETTING ITEMS
     */
    @Query("SELECT * FROM $shelfItemsTable WHERE id = :id")
    fun getById(id: Long): Flow<ShelfPalItem?>

    @Query(
        """
            SELECT * FROM $shelfItemsTable
            WHERE (:category IS NULL OR category = :category)
            AND (:month IS NULL OR expiryMonth = :month)
            AND (:year IS NULL OR expiryYear = :year)
            AND used = :used
        """
    )
    fun getItems(
        category: String?,
        month: Int?,
        year: Int?,
        used: Boolean = false
    ): Flow<List<ShelfPalItem>>

    @Query("""
            SELECT * FROM $shelfItemsTable 
            WHERE used = 0
        """
    )
    suspend fun getItems(): List<ShelfPalItem>

    @Query("""
            SELECT * FROM $shelfItemsTable 
            WHERE id IN (:items) 
            AND used = 0
        """
    )
    fun getItems(items: List<Long>): Flow<List<ShelfPalItem>>

    @Query(
        """
            SELECT * FROM $shelfItemsTable
            WHERE expiryMonth <= :month         
            AND expiryYear <= :year
            AND used = 0
        """
    )
    fun getExpiredItems(month: Int, year: Int): Flow<List<ShelfPalItem>>

    /**
     *  END REGION: GETTING ITEMS
     */
}