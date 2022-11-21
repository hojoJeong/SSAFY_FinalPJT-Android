package com.ssafy.silencelake.fragment.main.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListRecentOrderDetailBinding
import com.ssafy.silencelake.dto.OrderDetail

class RecentOrderDetailAdapter(context: Context) :
    RecyclerView.Adapter<RecentOrderDetailAdapter.RecentOrderDetailViewHolder>() {
    var itemList = mutableListOf<OrderDetail>()
    val mContext = context
    inner class RecentOrderDetailViewHolder(val binding: ItemListRecentOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OrderDetail) {
            val resId = mContext.resources.getIdentifier("iceamericano", "drawable", mContext.packageName)
            binding.apply {
                imgItemRecentorderDetail.setImageResource(resId)
                tvProductnameRecentordeDetail.text = "아메리카노"
                tvQuantityRecentorderDetail.text = data.quantity.toString()
                tvUnitpriceRecentorderDetail.text = "1000 원"
                tvPriceRecentorderDetail.text = "${(1000 * data.quantity).toString()} 원"
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