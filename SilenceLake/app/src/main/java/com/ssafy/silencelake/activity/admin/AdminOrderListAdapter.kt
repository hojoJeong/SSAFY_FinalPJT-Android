package com.ssafy.silencelake.activity.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListAdminBinding
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.smartstore.response.OrderDetailResponse
import java.text.SimpleDateFormat

class AdminOrderListAdapter() :
    RecyclerView.Adapter<AdminOrderListAdapter.AdminOrderListViewHolder>() {
    var orderList = mutableListOf<OrderDto>()
    var orderDetailList = mutableListOf<MutableList<OrderDetailResponse>>()
    lateinit var onClick: onBtnClickListener
    private lateinit var orderName:String
    private lateinit var orderDate: String
    private lateinit var totalPrice: String
    inner class AdminOrderListViewHolder(val binding: ItemListAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OrderDto, orderDetailList: MutableList<OrderDetailResponse>) {
            binding.apply {
                formatData(data, orderDetailList)

                binding.apply {
                    tvOrdernameAdmin.text = orderName
                    tvOrderdateAdmin.text = orderDate
                    tvTotalpriceAdmin.text = totalPrice
                }

                binding.apply {
                    btnCompleteAdmin.setOnClickListener {
                        onClick.onCompleteBtnClickListener()
                    }
                    btnCancelAdmin.setOnClickListener {
                        onClick.onCancelBtnClickListener()
                    }
                    btnFoldAdmin.setOnClickListener {
                        onClick.onFoldBtnClickListener(orderDetailList, binding, data.isExpended)
                        data.isExpended = !data.isExpended
                    }
                }
                orderCompleteBtnListener()
                orderCancelBtnListener()
                foldBtnListener(orderDetailList, binding, data.isExpended)

            }
        }

        private fun formatData(data:OrderDto, orderDetailList: MutableList<OrderDetailResponse>){
            orderName = if (orderDetailList.size == 1) {
                orderDetailList[0].productName
            } else {
                "${orderDetailList[0].productName} 외 ${orderDetailList.size - 1}건"
            }

            var price = 0
            for (i in 0 until orderDetailList.size) {
                price += orderDetailList[i].totalPrice
            }
            totalPrice = "총 가격 : $price 원"

            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
            orderDate = dateFormat.format(data.orderTime)
        }

        private fun orderCompleteBtnListener(){
            binding.btnCompleteAdmin.setOnClickListener {
                onClick.onCompleteBtnClickListener()
            }
        }

        private fun orderCancelBtnListener(){
            binding.btnCancelAdmin.setOnClickListener {
                onClick.onCancelBtnClickListener()
            }
        }

        private fun foldBtnListener(orderDetailList: MutableList<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean){
            binding.btnFoldAdmin.setOnClickListener {
                onClick.onFoldBtnClickListener(orderDetailList, binding, isExpended)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminOrderListViewHolder {
        val view = ItemListAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminOrderListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminOrderListViewHolder, position: Int) {
        holder.bind(orderList[position], orderDetailList[position])
    }

    override fun getItemCount(): Int = orderList.size

    interface onBtnClickListener {
        fun onFoldBtnClickListener(orderDetailList: MutableList<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean)
        fun onCompleteBtnClickListener()
        fun onCancelBtnClickListener()
    }
}