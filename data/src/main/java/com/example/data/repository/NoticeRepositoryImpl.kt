package com.example.data.repository

import com.example.domain.model.NoticeRequest
import com.example.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(

) :NoticeRepository{
    override suspend fun requestNotice(
        postId: String,
        targetChannelId: String,
        requesterUserId: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoticeRequestsForChannel(channelId: String): Result<List<NoticeRequest>> {
        TODO("Not yet implemented")
    }

    override suspend fun approveNotice(noticeRequestId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun rejectNotice(noticeRequestId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun distributeNotice(noticeId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}