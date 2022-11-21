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

    fun getProductList(callback: RetrofitCallback<List<ProductDto>>)  {
        val menuInfoRequest: Call<List<ProductDto>> = RetrofitUtil.productService.getProductList()
        menuInfoRequest.enqueue(object : Callback<List<ProductDto>> {
            override fun onResponse(call: Call<List<ProductDto>>, response: Response<List<ProductDto>>) {
                val res = response.body()
                if(response.code() == 200){
                    if (res != null) {
                        callback.onSuccess(response.code(), res)
                    }
                } else {
                    callback.onFailure(response.code())
                }
            }

            override fun onFailure(call: Call<List<ProductDto>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
    suspend fun getProductWithComments(productId: Int):List<MenuDetailWithCommentResponse> {
        val response = RetrofitUtil.productService.getProductWithComments(productId)
        if(response.isSuccessful){
            return response.body() ?: emptyList()
        }
        else return emptyList()
    }
}