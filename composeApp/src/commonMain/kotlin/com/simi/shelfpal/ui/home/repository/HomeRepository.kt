package com.simi.shelfpal.ui.home.repository

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getItems(): Flow<List<ShelfPalItem>>
}