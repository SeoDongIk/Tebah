package com.example.data.source.remote.impl

import com.example.data.model.dto.ChurchDto
import com.example.data.source.remote.ChurchRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChurchRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChurchRemoteDataSource {

    override suspend fun createChurch(church: ChurchDto): Result<Unit> = runCatching {
        firestore.collection("churches")
            .document(church.id)
            .set(church)
            .await()
    }

    override suspend fun getChurchById(churchId: String): Result<ChurchDto> = runCatching {
        val snapshot = firestore.collection("churches")
            .document(churchId)
            .get()
            .await()

        snapshot.toObject(ChurchDto::class.java)
            ?: throw NoSuchElementException("No church found with id: $churchId")
    }

    override suspend fun getApprovedChurchList(): Result<List<ChurchDto>> = runCatching {
        firestore.collection("churches")
            .whereEqualTo("isApproved", true)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(ChurchDto::class.java) }
    }
}
