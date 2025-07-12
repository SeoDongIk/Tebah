package com.example.data.source.remote.impl

import com.example.data.model.dto.ChannelDto
import com.example.data.source.remote.ChannelRemoteDataSource
import com.example.domain.model.ChannelCreateRequest
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ChannelRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChannelRemoteDataSource {
    override suspend fun createChannel(request: ChannelCreateRequest): Result<ChannelDto> {
        // Firestore 저장 로직
        TODO()
    }
    override suspend fun approveChannel(channelId: String): Result<Unit> = TODO()
    override suspend fun getChannelsByChurchId(churchId: String): Result<List<ChannelDto>> = TODO()
    override suspend fun subscribeToChannel(userId: String, channelId: String): Result<Unit> = TODO()
    override suspend fun getChannelSubscriptions(userId: String): Result<List<ChannelDto>> = TODO()
}