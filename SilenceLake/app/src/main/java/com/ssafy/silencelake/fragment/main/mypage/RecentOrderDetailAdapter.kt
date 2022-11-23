package com.ssafy.silencelake.fragment.main.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.silencelake.databinding.ItemListRecentOrderDetailBinding
import com.ssafy.smartstore.response.OrderDetailResponse

class RecentOrderDetailAdapter(context: Context) :
    RecyclerView.Adapter<RecentOrderDetailAdapter.RecentOrderDetailViewHolder>() {
    var itemList = mutableListOf<OrderDetailResponse>()
    val mContext = context
    inner class RecentOrderDetailViewHolder(val binding: ItemListRecentOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OrderDetailResponse) {

            Glide.with(mContext).load(data.img).into(binding.imgItemDetailRecentorder)
            binding.apply {
                tvProductnameDetailRecentorder.text = data.productName
                tvQuantityDetailRecentorder.text = "${data.quantity} 개"
                tvUnitpriceDetailRecentorder.text = "${data.unitPrice} 원"
                tvPriceDetailRecentorder.text = "${data.totalPrice} 원"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentOrderDetailViewHolder {
        val view = ItemListRecentOrderDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentOrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentOrderDetailViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}