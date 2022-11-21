package com.ssafy.silencelake.dto

data class CommentDto(
    val id: Int = -1,
    val userId: String,
    var productId: Int,
    val rating: Float,
    val comment: String
)