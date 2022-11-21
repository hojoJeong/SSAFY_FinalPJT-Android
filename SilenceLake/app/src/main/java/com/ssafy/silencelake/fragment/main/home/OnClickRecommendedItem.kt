package com.ssafy.silencelake.fragment.main.home

import com.ssafy.silencelake.dto.ProductDto

interface OnClickRecommendedItem {
    fun onClick(product: ProductDto)
}