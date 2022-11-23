package com.ssafy.smartstore.service

import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.smartstore.response.MenuDetailWithCommentResponse

private const val TAG = "UserService_싸피"

class ProductRepository {

    companion object{
        suspend fun getProductList(): List<ProductDto> {
            val response = RetrofitUtil.productService.getProductList()
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else return emptyList()
        }

        suspend fun getProductWithComments(productId: Int): List<MenuDetailWithCommentResponse> {
            val response = RetrofitUtil.productService.getProductWithComments(productId)
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else return emptyList()
        }

        suspend fun getRecommendedProduct(userId: String): List<ProductDto> {
            val response = RetrofitUtil.productService.getRecommendedProduct(userId)
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else return emptyList()
        }
    }
}