package com.ssafy.silencelake.dto

data class OrderDto(var completed: Char, var orderDetail: MutableList<OrderDetail>, val id: Int, var orderTable: String, var orderTime: String, var stamp: Stamp, var userId: String) {
}