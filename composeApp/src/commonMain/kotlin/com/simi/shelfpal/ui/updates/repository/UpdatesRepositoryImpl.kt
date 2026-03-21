package com.simi.shelfpal.ui.updates.repository

import com.simi.shelfpal.data.database.daos.ExpiryUpdatesDao
import com.simi.shelfpal.data.database.entities.ExpiryStatus

class UpdatesRepositoryImpl(
    private val updatesDao: ExpiryUpdatesDao
) : UpdatesRepository {

    override suspend fun getUpdates(
        status: ExpiryStatus,
        isReviewed: Boolean
    ) = updatesDao.getUpdates(
        status = status,
        isReviewed = isReviewed
    )
}