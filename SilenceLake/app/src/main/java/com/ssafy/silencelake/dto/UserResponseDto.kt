package com.ssafy.silencelake.dto

data class UserResponseDto(var grade: GradeDto, var user: UserDto, var order: MutableList<OrderDto>) {
}