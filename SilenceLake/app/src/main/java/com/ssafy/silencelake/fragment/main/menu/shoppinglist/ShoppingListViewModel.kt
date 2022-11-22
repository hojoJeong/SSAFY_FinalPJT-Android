package com.ssafy.silencelake.fragment.main.menu.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ssafy.smartstore.response.MenuDetailWithCommentResponse
import com.ssafy.smartstore.service.ProductService
import kotlinx.coroutines.launch

class ShoppingListViewModel: ViewModel() {
    //선택된 하나의 메뉴
    private val _selectedProduct = MutableLiveData<List<MenuDetailWithCommentResponse>>()
    val selectedProduct: LiveData<List<MenuDetailWithCommentResponse>>
        get() = _selectedProduct
    fun getSelectedProduct(id: Int) = viewModelScope.launch{
        _selectedProduct.value = ProductService().getProductWithComments(id)
    }
}