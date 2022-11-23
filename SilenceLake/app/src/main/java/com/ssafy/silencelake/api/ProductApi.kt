package com.ssafy.smartstore.api



import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.smartstore.response.MenuDetailWithCommentResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductApi {
    // 전체 상품의 목록을 반환한다
    @GET("rest/product")
    suspend fun getProductList(): Response<List<ProductDto>>

    // {productId}에 해당하는 상품의 정보를 comment와 함께 반환한다.
    // comment 조회시 사용
    @GET("rest/product/{productId}")
    suspend fun getProductWithComments(@Path("productId") productId: Int): Response<List<MenuDetailWithCommentResponse>>

    //HomeFragment에서 사용할 추천메뉴
    @GET("rest/product/recommended/{userId}")
    suspend fun getRecommendedProduct(@Path("userId") userId: String): Response<List<ProductDto>>
}