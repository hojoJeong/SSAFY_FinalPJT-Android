package com.ssafy.smartstore.api


import com.ssafy.silencelake.dto.CommentDto
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {
    //comment를 가져온다!!!
    @GET("rest/comment/{productId}")
    suspend fun getComment(@Path("productId") productId: Int) : Response<List<CommentDto>>

    // comment를 추가한다.
    @POST("rest/comment")
    suspend fun insert(@Body comment: CommentDto): Response<Boolean>

    // comment를 수정한다.
    @PUT("rest/comment")
    suspend fun update(@Body comment: CommentDto): Response<Boolean>

    // {id}에 해당하는 comment를 삭제한다.
    @DELETE("rest/comment/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Boolean>
}