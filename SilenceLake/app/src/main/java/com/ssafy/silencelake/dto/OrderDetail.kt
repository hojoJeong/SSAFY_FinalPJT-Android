package com.ssafy.silencelake.dto

data class OrderDetail(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    var quantity: Int,
    var volume: String
) {
    constructor(productId: Int, quantity: Int, volume: String) : this(
        0,
        0,
        productId,
        quantity,
        volume
    )
}