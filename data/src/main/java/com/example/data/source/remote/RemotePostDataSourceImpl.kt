package com.example.data.source.remote

import com.example.domain.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RemotePostDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RemotePostDataSource {

//    private val postsCollection = firestore.collection("posts")
//
//    override suspend fun uploadPost(post: Post): Result<Unit> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getPosts(): List<Post> {
//        TODO("Not yet implemented")
//    }
}