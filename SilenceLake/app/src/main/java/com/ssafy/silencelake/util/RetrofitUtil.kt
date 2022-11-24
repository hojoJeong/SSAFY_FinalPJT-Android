package com.ssafy.silencelake.util

import com.ssafy.silencelake.api.UserApi
import com.ssafy.smartstore.api.CommentApi
import com.ssafy.smartstore.api.OrderApi
import com.ssafy.smartstore.api.ProductApi

class RetrofitUtil {
    companion object{
        val orderApi = ApplicationClass.retrofit.create(OrderApi::class.java)
        val productApi = ApplicationClass.retrofit.create(ProductApi::class.java)
        val userApi = ApplicationClass.retrofit.create(UserApi::class.java)
        val commentApi = ApplicationClass.retrofit.create(CommentApi::class.java)
    }
}