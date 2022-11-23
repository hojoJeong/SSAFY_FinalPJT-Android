package com.ssafy.silencelake.fragment.main.menu.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.smartstore.service.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel(){

    private var _comment: CommentDto = CommentDto(-1,"", -1, -1f, "")
    val comment: CommentDto
        get() = _comment

    fun getComment(productId: Int){

    }
    fun insertComment(commentDto: CommentDto) = viewModelScope.launch {
        CommentRepository.insertComment(commentDto)
    }

    fun updateComment(commentDto: CommentDto) = viewModelScope.launch {
        CommentRepository.updateComment(commentDto)
    }

    fun deleteComment(id: Int) = viewModelScope.launch {
        CommentRepository.deleteComment(id)
    }

}