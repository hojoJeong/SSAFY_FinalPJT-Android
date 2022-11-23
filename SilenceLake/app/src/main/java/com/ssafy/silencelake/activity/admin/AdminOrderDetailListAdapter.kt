package com.ssafy.silencelake.activity.admin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListDetailAdminBinding
import com.ssafy.smartstore.response.OrderDetailResponse

class AdminOrderDetailListAdapter : RecyclerView.Adapter<AdminOrderDetailListAdapter.AdminDetailViewHolder>(){
    var orderDetailList = mutableListOf<OrderDetailResponse>()
    inner class AdminDetailViewHolder(val binding: ItemListDetailAdminBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDetailViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AdminDetailViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}