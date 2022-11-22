package com.ssafy.smartstore.service

import android.content.Context
import android.util.Log
import android.view.Menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.silencelake.util.RetrofitCallback
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.smartstore.response.MenuDetailWithCommentResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "UserService_싸피"

class ProductService {
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
}