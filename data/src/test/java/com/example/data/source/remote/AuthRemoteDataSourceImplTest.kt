package com.example.data.source.remote

import com.example.data.model.dto.ChurchDto
import com.example.data.model.dto.UserDto
import com.example.data.source.remote.impl.AuthRemoteDataSourceImpl
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class AuthRemoteDataSourceImplTest {

    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)
    private val firestore: FirebaseFirestore = mockk()
    private val dataSource = AuthRemoteDataSourceImpl(firebaseAuth, firestore)

    @Test
    fun getUserById_success_returnsUser() = runTest {
        val user = UserDto(id = "123", email = "e", name = "n", role = "r", churchId = "c")
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()
        val snapshot = mockk<DocumentSnapshot>()

        every { firestore.collection("users") } returns collection
        every { collection.document("123") } returns document
        every { document.get() } returns Tasks.forResult(snapshot)
        every { snapshot.toObject(UserDto::class.java) } returns user

        val result = dataSource.getUserById("123")
        assertEquals(user, result)
    }

    @Test
    fun getUserById_failure_throwsException() = runTest {
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()
        val exception = Exception("network")

        every { firestore.collection("users") } returns collection
        every { collection.document("123") } returns document
        every { document.get() } returns Tasks.forException(exception)

        try {
            dataSource.getUserById("123")
            fail("Exception expected")
        } catch (e: Exception) {
            assertEquals("network", e.message)
        }
    }

    @Test
    fun saveUser_success_writesDocument() = runTest {
        val user = UserDto(id = "123", email = "", name = "", role = "", churchId = "")
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()

        every { firestore.collection("users") } returns collection
        every { collection.document("123") } returns document
        every { document.set(user) } returns Tasks.forResult(null)

        dataSource.saveUser(user)
        verify { document.set(user) }
    }

    @Test
    fun saveUser_failure_throwsException() = runTest {
        val user = UserDto(id = "123", email = "", name = "", role = "", churchId = "")
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()
        val exception = Exception("write")

        every { firestore.collection("users") } returns collection
        every { collection.document("123") } returns document
        every { document.set(user) } returns Tasks.forException(exception)

        try {
            dataSource.saveUser(user)
            fail("Exception expected")
        } catch (e: Exception) {
            assertEquals("write", e.message)
        }
    }

    @Test
    fun saveChurch_success_writesDocument() = runTest {
        val church = ChurchDto(
            id = "c1",
            name = "n",
            profileImageUrl = null,
            region = "r",
            phone = "p",
            description = "d",
            createdAt = Timestamp.now(),
            adminId = "a",
            adminName = "an",
            adminPosition = "ap"
        )
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()

        every { firestore.collection("churches") } returns collection
        every { collection.document("c1") } returns document
        every { document.set(church) } returns Tasks.forResult(null)

        dataSource.saveChurch(church)
        verify { document.set(church) }
    }

    @Test
    fun saveChurch_failure_throwsException() = runTest {
        val church = ChurchDto(
            id = "c1",
            name = "n",
            profileImageUrl = null,
            region = "r",
            phone = "p",
            description = "d",
            createdAt = Timestamp.now(),
            adminId = "a",
            adminName = "an",
            adminPosition = "ap"
        )
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()
        val exception = Exception("fail")

        every { firestore.collection("churches") } returns collection
        every { collection.document("c1") } returns document
        every { document.set(church) } returns Tasks.forException(exception)

        try {
            dataSource.saveChurch(church)
            fail("Exception expected")
        } catch (e: Exception) {
            assertEquals("fail", e.message)
        }
    }

    @Test
    fun createUser_success_returnsUid() = runTest {
        val authResult = mockk<AuthResult>()
        val user = mockk<FirebaseUser>()

        every { firebaseAuth.createUserWithEmailAndPassword("e", "p") } returns Tasks.forResult(authResult)
        every { authResult.user } returns user
        every { user.uid } returns "uid123"

        val uid = dataSource.createUser("e", "p")
        assertEquals("uid123", uid)
    }

    @Test
    fun createUser_failure_throwsException() = runTest {
        val exception = Exception("create")
        every { firebaseAuth.createUserWithEmailAndPassword("e", "p") } returns Tasks.forException(exception)

        try {
            dataSource.createUser("e", "p")
            fail("Exception expected")
        } catch (e: Exception) {
            assertEquals("create", e.message)
        }
    }
}

