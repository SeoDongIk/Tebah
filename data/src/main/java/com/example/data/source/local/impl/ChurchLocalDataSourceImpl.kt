package com.example.data.source.local.impl

import com.example.data.model.entity.ChurchEntity
import com.example.data.source.local.ChurchLocalDataSource
import com.example.data.source.local.database.dao.ChurchDao
import javax.inject.Inject

class ChurchLocalDataSourceImpl @Inject constructor(
    private val churchDao: ChurchDao
) : ChurchLocalDataSource {
    override suspend fun saveChurch(church: ChurchEntity) {
        churchDao.insert(church)
    }

    override suspend fun getChurchById(churchId: String): ChurchEntity? {
        return churchDao.getChurchById(churchId)
    }

    override suspend fun deleteChurch(churchId: String) {
        return churchDao.deleteChurchById(churchId)
    }
}