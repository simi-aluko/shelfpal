package com.simi.shelfpal.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.simi.shelfpal.data.database.entities.ExpiryStatus
import com.simi.shelfpal.data.database.entities.ExpiryUpdate
import com.simi.shelfpal.data.database.entities.UpdateWithItem

@Dao
interface ExpiryUpdatesDao {

    @Transaction
    @Query("""
    SELECT * FROM expiry_updates 
    WHERE expiryStatus = :status 
    AND (
            (:status = 0 AND expiredReviewed = :isReviewed) OR
            (:status = 1 AND todayReviewed = :isReviewed) OR
            (:status = 2 AND weekReviewed = :isReviewed) OR
            (:status = 3 AND monthReviewed = :isReviewed) OR
            (:status = 4 AND customDurationReviewed = :isReviewed)
        )
    """)
    suspend fun getUpdates(
        status: ExpiryStatus,
        isReviewed: Boolean
    ): List<UpdateWithItem>

    @Update
    suspend fun update(update: ExpiryUpdate)

    @Insert
    suspend fun insert(update: ExpiryUpdate)

    @Query("SELECT * FROM expiry_updates WHERE itemId = :itemId")
    suspend fun getByItemId(itemId: Long): ExpiryUpdate?
}