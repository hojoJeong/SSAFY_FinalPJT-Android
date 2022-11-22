package com.ssafy.silencelake.dto

data class OrderDetailResponseDto(
    var img: String,
    var quantity: Int,
    var totalprice: Int,
    var product_id: Int,
    var o_id: Int,
    var name: String,
    var stamp: Int,
    var order_time: String,
    var type: String,
    var unitprice: Int,
    var order_table: String
) {
}