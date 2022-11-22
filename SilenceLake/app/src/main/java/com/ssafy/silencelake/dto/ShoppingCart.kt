package com.ssafy.silencelake.dto


data class ShoppingCart(
    val menuId: Int,
    val menuImg: String,
    val menuName: String,
    var menuCnt: Int,
    val menuPrice: Int,
    val type: String,
    val volume: String
) {
    var totalPrice: Int = menuCnt * menuPrice
    fun addDupMenu(shoppingCart: ShoppingCart) {
        this.menuCnt += shoppingCart.menuCnt
        this.totalPrice = this.menuCnt * this.menuPrice
    }
}