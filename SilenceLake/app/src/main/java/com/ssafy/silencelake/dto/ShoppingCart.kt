package com.ssafy.silencelake.dto


data class ShoppingCart(
    val menuId: Int,
    val menuImg: String,
    val menuName: String,
    val menuNameEng: String,
    var menuCnt: Int,
    val menuPrice: Int,
    val type: String,
    val volume: String
) {
    val totalPrice: Int
        get() = menuCnt * menuPrice
}