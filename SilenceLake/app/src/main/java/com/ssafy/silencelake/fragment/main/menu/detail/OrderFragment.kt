package com.ssafy.silencelake.fragment.main.menu.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.silencelake.R
import com.ssafy.silencelake.activity.MainActivity
import com.ssafy.silencelake.databinding.FragmentOrderBinding
import com.ssafy.silencelake.dto.OrderDetail
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.silencelake.dto.ShoppingCart
import com.ssafy.silencelake.dto.Stamp
import com.ssafy.silencelake.fragment.main.menu.shoppinglist.ShoppingListViewModel
import com.ssafy.silencelake.util.ApplicationClass
import java.util.*

private const val TAG = "OrderFragment_싸피"
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
        Log.d(TAG, "onViewCreated: ${activityViewModel.productId}")
        initCLickListener()
    }

    fun updateTotalPrice() {
        binding.textTotalPriceOrder.text = "${
            activityViewModel.selectedProduct.value!!.get(0)!!.productPrice * binding.textQuantityOrder.text.toString()
                .toInt()
        }원"
    }
    fun initCLickListener(){
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
                var checkedChip = "tall"
                when(chipGroupCoffeeSizeOrder.checkedChipId){
                    R.id.chip_tall -> checkedChip = "tall"
                    R.id.chip_grande -> checkedChip = "grande"
                    R.id.chip_venti -> checkedChip = "venti"
                }
                val item = activityViewModel.selectedProduct.value?.get(0)!!
                activityViewModel.list.add(ShoppingCart(activityViewModel.productId, item.productImg, item.productName, textQuantityOrder.text.toString().toInt(),
                    item.productPrice, item.type, checkedChip))
                activityViewModel.updateShoppingList()
                activity!!.onBackPressed()
            }
        }
    }
}