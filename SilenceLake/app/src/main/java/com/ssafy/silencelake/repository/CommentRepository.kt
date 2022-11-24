package com.ssafy.smartstore.service

import android.util.Log
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.silencelake.util.RetrofitUtil

class CommentRepository {
    companion object{
        suspend fun getComment(productId: Int): List<CommentDto> {
            val response = RetrofitUtil.commentApi.getComment(productId)
            if(response.isSuccessful){
                Log.d("CommentService", "getComment: complete getComment")
                return response.body()?: emptyList()
            } else {
                Log.d("CommentService", "getComment: fail to getComment")
                return emptyList()
            }
        }
        suspend fun insertComment(comment: CommentDto): Boolean{
            val response = RetrofitUtil.commentApi.insert(comment)
            if(response.isSuccessful){
                Log.d("CommentService", "insertComment: complete insert comment")
                return true
            } else{
                Log.d("CommentService", "insertComment: fail to insert comment")
                return false
            }
        }
        suspend fun updateComment(comment: CommentDto): Boolean{
            val response = RetrofitUtil.commentApi.update(comment)
            if(response.isSuccessful){
                Log.d("CommentService", "updateComment: complete updateComment")
                return true
            } else{
                Log.d("CommentService", "updateComment: fail to updateComment")
                return false
            }
        }
        suspend fun deleteComment(id: Int): Boolean{
            val response = RetrofitUtil.commentApi.delete(id)
            if(response.isSuccessful){
                Log.d("CommentService", "deleteComment: complete deleteComment")
                return true
            } else{
                Log.d("CommentService", "deleteComment: fail to deleteComment")
                return false
            }
        }
    }
}