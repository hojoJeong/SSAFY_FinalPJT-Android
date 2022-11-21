package com.ssafy.smartstore.service

import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.silencelake.util.RetrofitUtil

class CommentService {
    suspend fun insertComment(comment: CommentDto){
        val response = RetrofitUtil.commentService.insert(comment)
    }
    suspend fun updateComment(comment: CommentDto){
        val response = RetrofitUtil.commentService.update(comment)
    }
    suspend fun deleteComment(id: Int){
        val response = RetrofitUtil.commentService.delete(id)
    }
}