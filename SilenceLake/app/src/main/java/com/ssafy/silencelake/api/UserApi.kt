package com.ssafy.silencelake.api

import com.ssafy.silencelake.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    // 사용자 정보를 추가한다.
    @POST("rest/user")
    fun insert(@Body body: UserDto): Call<Boolean>

    // 사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다.
    @POST("rest/user/info")
    fun getInfo(@Query("id") id: String): Call<HashMap<String, Any>>

    // request parameter로 전달된 id가 이미 사용중인지 반환한다.
    @GET("rest/user/isUsed")
    fun isUsedId(@Query("id") id: String): Call<Boolean>

    // 로그인 처리 후 성공적으로 로그인 되었다면 loginId라는 쿠키를 내려준다.
    @POST("rest/user/login")
    fun login(@Body body: UserDto): Call<UserDto>
}