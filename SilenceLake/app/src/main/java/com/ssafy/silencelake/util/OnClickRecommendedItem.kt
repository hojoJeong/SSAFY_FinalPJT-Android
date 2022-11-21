package com.ssafy.silencelake.util

import com.ssafy.silencelake.dto.ProductDto

interface OnClickRecommendedItem {
    fun onClick(product: ProductDto)
}