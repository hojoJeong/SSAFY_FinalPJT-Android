package com.ssafy.silencelake.activity.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.silencelake.databinding.ItemListDetailAdminBinding
import com.ssafy.smartstore.response.OrderDetailResponse

class AdminOrderDetailListAdapter(val context: Context) : RecyclerView.Adapter<AdminOrderDetailListAdapter.AdminDetailViewHolder>(){
    var orderDetailList = listOf<OrderDetailResponse>()
    inner class AdminDetailViewHolder(val binding: ItemListDetailAdminBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : OrderDetailResponse){

            Glide.with(context).load(data.img).into(binding.imgItemDetailAdmin)
            binding.apply {
                tvProductnameDetailAdmin.text = data.productName
                tvQuantityDetailAdmin.text = data.quantity.toString()
                tvUnitpriceDetailAdmin.text = data.unitPrice.toString()
                tvPriceDetailAdmin.text = data.totalPrice.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDetailViewHolder {
        val view = ItemListDetailAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminDetailViewHolder, position: Int) {
        holder.bind(orderDetailList[position])
    }

    override fun getItemCount(): Int = orderDetailList.size
}