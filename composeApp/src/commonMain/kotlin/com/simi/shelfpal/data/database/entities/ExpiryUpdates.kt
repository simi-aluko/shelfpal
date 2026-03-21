package com.simi.shelfpal.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.simi.shelfpal.data.database.expiryUpdatesTable

@Entity(
    tableName = expiryUpdatesTable,
    foreignKeys = [
        ForeignKey(
            entity = ShelfPalItem::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["itemId"], unique = true)],
)
data class ExpiryUpdates(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val itemId: Long,
    val expiryStatus: ExpiryStatus,
    val monthReviewed: Boolean = false,
    val weekReviewed: Boolean = false,
    val todayReviewed: Boolean = false,
    val customDurationReviewed: Boolean = false,
    val expiredReviewed: Boolean = false,
    val updatedAt: Long
)

enum class ExpiryStatus(val status: Int) {
    EXPIRED(0),
    EXPIRING_TODAY(1),
    EXPIRING_IN_A_WEEK(2),
    EXPIRING_IN_A_MONTH(3),
    EXPIRING_IN_CUSTOM_DURATION(4)
}

class ExpiryStatusTypeConverter {
    @TypeConverter
    fun fromStatus(status: ExpiryStatus) = status.status

    @TypeConverter
    fun toStatus(status: Int) = ExpiryStatus.entries.first { it.status == status }
}

data class UpdateWithItem(
    @Embedded val expiryUpdate: ExpiryUpdates,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "id"
    )
    val item: ShelfPalItem
)