package com.ssafy.silencelake.fragment.main.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.silencelake.service.UserService
import com.ssafy.silencelake.util.RetrofitUtil
import com.ssafy.smartstore.service.ProductService
import kotlinx.coroutines.launch

class ProductMenuViewModel : ViewModel() {
    private var _menuProudctList = MutableLiveData<List<ProductDto>>()
    private var _recommendedMenuList = MutableLiveData<MutableList<ProductDto>>()

    val menuProductList: LiveData<List<ProductDto>>
        get() = _menuProudctList

    val recommendedMenuLiveData: LiveData<MutableList<ProductDto>>
        get() = _recommendedMenuList

    fun getProductList() = viewModelScope.launch {
        val response = ProductService().getProductList()
        _menuProudctList.value = response
    }

    fun getRecommendedMenu() =
        viewModelScope.launch {
            val response = RetrofitUtil.productService.getRecommendedMenu()
            if (response.isSuccessful) {
                _recommendedMenuList.value = response.body() as MutableList<ProductDto>?
                Log.d("추천메뉴", "getRecommendedMenu: 추천메뉴 호출 성공")
            } else {
                val list = mutableListOf<ProductDto>()
                list.add(
                    ProductDto(
                        11,
                        "콜드 브루",
                        "Cold Brew",
                        "coffee",
                        4900,
                        "https://ifh.cc/g/3gT3rH.png"
                    )
                )
                list.add(
                    ProductDto(
                        11,
                        "콜드 브루",
                        "Cold Brew",
                        "coffee",
                        4900,
                        "https://ifh.cc/g/3gT3rH.png"
                    )
                )
                list.add(
                    ProductDto(
                        11,
                        "콜드 브루",
                        "Cold Brew",
                        "coffee",
                        4900,
                        "https://ifh.cc/g/3gT3rH.png"
                    )
                )

                _recommendedMenuList.value = list
                Log.d("추천메뉴", "getRecommendedMenu: 추천메뉴 호출 실패")
            }
        }

}