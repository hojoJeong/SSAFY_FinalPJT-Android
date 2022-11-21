package com.ssafy.silencelake.fragment.main.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListRecentOrderBinding
import com.ssafy.silencelake.databinding.ItemListRecentOrderDetailBinding
import com.ssafy.silencelake.dto.OrderDto

class RecentOrderAdapter: RecyclerView.Adapter<RecentOrderAdapter.RecentOrderViewHolder>() {
    var orderList = mutableListOf<OrderDto>()
    lateinit var onFoldButtonLIstener: OnFoldButtonLIstener
    inner class RecentOrderViewHolder(val binding: ItemListRecentOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: OrderDto){
            binding.apply {
                tvOrdernameRecentorder.text = "아메리카노 외 ${data.orderDetail.size-1}건"
                tvOrderdateRecentorder.text = data.orderTime

                btnFoldRecentorder.setOnClickListener {
                    onFoldButtonLIstener.onClick(data.orderDetail, binding)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentOrderViewHolder {
        val view = ItemListRecentOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentOrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.size
}