package com.ssafy.silencelake.fragment.main.menu


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.smartstore.service.ProductRepository
import kotlinx.coroutines.launch

class ProductMenuViewModel : ViewModel() {
    private var _menuProudctList = MutableLiveData<List<ProductDto>>()
    private var _recommendedMenuList = MutableLiveData<List<ProductDto>>()

    val menuProductList: LiveData<List<ProductDto>>
        get() = _menuProudctList

    val recommendedMenuLiveData: LiveData<List<ProductDto>>
        get() = _recommendedMenuList

    fun getProductList() = viewModelScope.launch {
        val response = ProductRepository.getProductList()
        _menuProudctList.value = response
    }

    fun getRecommendedProduct(userId: String) = viewModelScope.launch {
             val response = ProductRepository.getRecommendedProduct(userId)
        _recommendedMenuList.value = response
        }
}