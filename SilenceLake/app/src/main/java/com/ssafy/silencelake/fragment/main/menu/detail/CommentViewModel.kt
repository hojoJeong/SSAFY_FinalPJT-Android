package com.ssafy.silencelake.fragment.main.menu.detail


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.silencelake.dto.CommentDto
import com.ssafy.smartstore.service.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentViewModel : ViewModel() {

    private var _comment = MutableLiveData<List<CommentDto>>()
    val comment: LiveData<List<CommentDto>>
        get() = _comment

    private var _productId: Int = 0
    val productId: Int
        get() = _productId

    fun getComment(productId: Int) = viewModelScope.launch {
        _productId = productId
        _comment.value = CommentRepository.getComment(productId)
    }

    fun insertComment(commentDto: CommentDto) = viewModelScope.launch {
        CommentRepository.insertComment(commentDto)
        getComment(_productId)
    }

    fun updateComment(commentDto: CommentDto) = viewModelScope.launch {
        CommentRepository.updateComment(commentDto)
        getComment(_productId)
    }

    fun deleteComment(id: Int) = viewModelScope.launch {
        CommentRepository.deleteComment(id)
        getComment(_productId)
    }
}