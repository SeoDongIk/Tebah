package com.example.data.source.remote

import com.example.domain.model.RootChannel
import com.example.domain.model.RootChannelSignUpRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteRootChannelDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteRootChannelDataSource {

//    override suspend fun requestRootChannelSignUp(request: RootChannelSignUpRequest): Result<Unit> = runCatching {
//        firestore.collection("root_signups").document(request.userId).set(request).await()
//    }
//
//    override suspend fun getRootChannels(): Result<List<RootChannel>> = runCatching {
//        firestore.collection("root_channels")
//            .get()
//            .await()
//            .documents.mapNotNull { it.toObject(RootChannel::class.java) }
//    }
}