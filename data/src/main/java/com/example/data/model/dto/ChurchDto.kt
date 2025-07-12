package com.example.data.model.dto

import com.google.firebase.Timestamp

/**
 * Firestore에 저장되는 교회 정보를 나타내는 데이터 전송 객체(DTO).
 *
 * 관리자 정보를 비정규화하여 포함하고 있으며,
 * Firebase-friendly 타입으로 구성되어 있음.
 *
 * @property id 교회 ID (Firestore 문서 ID).
 * @property name 교회 이름.
 * @property profileImageUrl 교회 프로필 이미지 URL. 없을 경우 null.
 * @property region 교회 지역 (Enum을 문자열로 변환한 값).
 * @property phone 교회 전화번호.
 * @property description 교회 소개 설명.
 * @property createdAt 교회 생성 시각 (Timestamp).
 * @property adminId 관리자 UID.
 * @property adminName 관리자 이름.
 * @property adminPosition 관리자 직분 (예: 담임목사, 부교역자 등).
 */
data class ChurchDto(
    val id: String,
    val name: String,
    val profileImageUrl: String?,
    val region: String,
    val phone: String,
    val description: String,
    val createdAt: Timestamp,
    val adminId: String,
    val adminName: String,
    val adminPosition: String
)