package com.ssafy.silencelake.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface FirebaseTokenApi {
    // Token정보 서버로 전송
    @POST("token")
    fun uploadToken(@Query("token") token: String): Call<String>

    //Token에 해당하는 유저에게 푸쉬알림 전송
    @POST("sendMessageTo")
    suspend fun sendMessageTo(@Query("title") title: String,@Query("body") body: String,@Query("token") token: String): Response<Unit>

    @POST("sendMessageToAdmin")
    suspend fun sendMessageToAdmin():Response<Unit>
}