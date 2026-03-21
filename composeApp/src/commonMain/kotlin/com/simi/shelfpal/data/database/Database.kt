package com.simi.shelfpal.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.simi.shelfpal.data.database.daos.ExpiryUpdatesDao
import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ExpiryStatusTypeConverter
import com.simi.shelfpal.data.database.entities.ExpiryUpdates
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductBasicInfoConverter
import com.simi.shelfpal.data.models.ProductExpiryInfoConverter
import com.simi.shelfpal.data.models.ShelfPalItemReminderSettingsConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "shelfpal_database.db"

@Database(entities = [ShelfPalItem::class, ExpiryUpdates::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(
    ProductBasicInfoConverter::class,
    ProductExpiryInfoConverter::class,
    ShelfPalItemReminderSettingsConverter::class,
    ExpiryStatusTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getShelfPalItemsDao(): ShelfPalItemsDao
    abstract fun getExpiryUpdatesDao(): ExpiryUpdatesDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

/**
 *  Database Table Names
 */
const val shelfItemsTable = "shelf_items"
const val expiryUpdatesTable = "expiry_updates"