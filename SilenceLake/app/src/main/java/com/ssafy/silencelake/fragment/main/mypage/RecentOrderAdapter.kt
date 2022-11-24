package com.ssafy.silencelake.fragment.main.mypage

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListRecentOrderBinding
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.smartstore.response.OrderDetailResponse
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class RecentOrderAdapter : RecyclerView.Adapter<RecentOrderAdapter.RecentOrderViewHolder>() {
    var orderList = mutableListOf<OrderDto>()
    var orderDetailList = mutableListOf<MutableList<OrderDetailResponse>>()
    lateinit var onFoldButtonLIstener: OnFoldButtonLIstener

    inner class RecentOrderViewHolder(val binding: ItemListRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderDto, orderDetailList: MutableList<OrderDetailResponse>) {
            binding.apply {
                var tPrice = 0;
                for (i in 0 until orderDetailList.size) {
                    tPrice += orderDetailList[i].totalPrice
                }
                val decimalFormat = DecimalFormat("#,##0")
                val totalPrice = "${decimalFormat.format(tPrice)} 원"
                var orderName = if (orderDetailList.size == 1) {
                    orderDetailList[0].productName
                } else {
                    "${orderDetailList[0].productName} 외 ${orderDetailList.size - 1}건"
                }
                val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
                val orderTime = dateFormat.format(order.orderTime)

                tvOrdernameRecentorder.text = orderName
                tvTotalpriceRecentorder.text = totalPrice.toString()
                tvOrderdateRecentorder.text = orderTime
                btnFoldRecentorder.setOnClickListener {
                    onFoldButtonLIstener.onClick(orderDetailList, binding, order.isExpended)
                    order.isExpended = !order.isExpended
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentOrderViewHolder {
        val view =
            ItemListRecentOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentOrderViewHolder, position: Int) {
        holder.bind(orderList[position], orderDetailList[position])
    }

    override fun getItemCount(): Int = orderList.size
}