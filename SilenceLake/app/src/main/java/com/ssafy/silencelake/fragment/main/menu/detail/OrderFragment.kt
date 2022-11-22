package com.ssafy.silencelake.fragment.main.menu.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.FragmentOrderBinding
import com.ssafy.silencelake.dto.OrderDetail
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.silencelake.dto.Stamp
import com.ssafy.silencelake.fragment.main.menu.shoppinglist.ShoppingListViewModel
import com.ssafy.silencelake.util.ApplicationClass
import java.util.*


class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private val activityViewModel by activityViewModels<ShoppingListViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateTotalPrice()
        binding.apply {
            buttonMinusOrder.setOnClickListener {
                if (textQuantityOrder.text.toString().toInt() > 1) {
                    textQuantityOrder.text = "${textQuantityOrder.text.toString().toInt() - 1}"
                    updateTotalPrice()
                }
            }
            buttonPlusOrder.setOnClickListener {
                textQuantityOrder.text = "${textQuantityOrder.text.toString().toInt() + 1}"
                updateTotalPrice()

            }
            buttonOrderOrder.setOnClickListener {
//                val id: Int,
//                var userId: String,
//                var orderTable: String,
//                var orderTime: Date,
//                var completed: Char,
//                var orderDetail: MutableList<OrderDetail>,
//                var stamp: Stamp
//                val order = OrderDto()
            }
        }
    }

    fun updateTotalPrice() {
        binding.textTotalPriceOrder.text = "${
            activityViewModel.selectedProduct.value!!.get(0)!!.productPrice * binding.textQuantityOrder.text.toString()
                .toInt()
        }원"
    }
}