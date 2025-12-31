package com.simi.shelfpal.ui.details.repository

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

interface ShelfPalItemDetailRepository {
    fun getItem(id: Long): Flow<ShelfPalItem>
}