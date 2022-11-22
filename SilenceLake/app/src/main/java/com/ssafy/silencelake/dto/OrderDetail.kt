package com.ssafy.silencelake.dto

data class OrderDetail(val id: Int, val orderId: Int, val productId: Int, var quantity: Int, var volume: String) {
}