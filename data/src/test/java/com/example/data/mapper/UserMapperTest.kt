package com.example.data.mapper

import com.example.data.model.dto.UserDto
import com.example.data.model.dto.UserProfileDto
import com.example.data.model.entity.UserEntity
import com.example.domain.model.User
import com.example.domain.model.UserProfile
import com.example.domain.model.UserRole
import org.junit.Assert.*
import org.junit.Test

class UserMapperTest {

    @Test
    fun userDto_toEntity() {
        val dto = UserDto(
            id = "1",
            email = "test@example.com",
            name = "Test",
            role = "MEMBER",
            isApproved = true,
            churchId = "church1",
            profile = UserProfileDto("img", "intro")
        )

        val entity = dto.toEntity()

        assertEquals("1", entity.id)
        assertEquals("test@example.com", entity.email)
        assertEquals("Test", entity.name)
        assertEquals("MEMBER", entity.role)
        assertEquals(true, entity.isApproved)
        assertEquals("church1", entity.churchId)
        assertEquals("img", entity.profile?.imageUrl)
        assertEquals("intro", entity.profile?.introduction)
    }

    @Test
    fun userEntity_toDomain() {
        val entity = UserEntity(
            id = "2",
            email = "admin@example.com",
            name = "Admin",
            role = "ADMIN",
            isApproved = false,
            churchId = "church2",
            profile = UserProfile("img2", "intro2")
        )

        val user = entity.toDomain()

        assertEquals("2", user.id)
        assertEquals("admin@example.com", user.email)
        assertEquals("Admin", user.name)
        assertEquals(UserRole.ADMIN, user.role)
        assertEquals(false, user.isApproved)
        assertEquals("church2", user.churchId)
        assertEquals("img2", user.profile?.imageUrl)
        assertEquals("intro2", user.profile?.introduction)
    }

    @Test
    fun user_toDto() {
        val user = User(
            id = "3",
            email = "guest@example.com",
            name = "Guest",
            role = UserRole.GUEST,
            isApproved = null,
            churchId = "church3",
            profile = UserProfile("img3", "intro3")
        )

        val dto = user.toDto()

        assertEquals("3", dto.id)
        assertEquals("guest@example.com", dto.email)
        assertEquals("Guest", dto.name)
        assertEquals("GUEST", dto.role)
        assertEquals(null, dto.isApproved)
        assertEquals("church3", dto.churchId)
        assertEquals("img3", dto.profile?.imageUrl)
        assertEquals("intro3", dto.profile?.introduction)
    }

    @Test
    fun userDto_toDomain() {
        val dto = UserDto(
            id = "4",
            email = "foo@example.com",
            name = "Foo",
            role = "ADMIN",
            isApproved = true,
            churchId = "church4",
            profile = UserProfileDto("img4", "intro4")
        )

        val user = dto.toDomain()

        assertEquals("4", user.id)
        assertEquals("foo@example.com", user.email)
        assertEquals("Foo", user.name)
        assertEquals(UserRole.ADMIN, user.role)
        assertEquals(true, user.isApproved)
        assertEquals("church4", user.churchId)
        assertEquals("img4", user.profile?.imageUrl)
        assertEquals("intro4", user.profile?.introduction)
    }
}

