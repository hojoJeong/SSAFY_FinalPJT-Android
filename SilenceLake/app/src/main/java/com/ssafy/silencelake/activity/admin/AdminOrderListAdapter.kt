package com.ssafy.silencelake.activity.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListAdminBinding
import com.ssafy.silencelake.dto.OrderDto
import com.ssafy.smartstore.response.OrderDetailResponse
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class AdminOrderListAdapter() :
    RecyclerView.Adapter<AdminOrderListAdapter.AdminOrderListViewHolder>() {
    var orderList = listOf<OrderDto>()
    var orderDetailList = listOf<List<OrderDetailResponse>>()
    lateinit var onBtnClickListener: OnBtnClickListener
    private lateinit var orderName:String
    private lateinit var orderDate: String
    private lateinit var totalPrice: String
    inner class AdminOrderListViewHolder(val binding: ItemListAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OrderDto, orderDetailList: List<OrderDetailResponse>) {
            binding.apply {
                formatData(data, orderDetailList)

                binding.apply {
                    tvOrdernameAdmin.text = orderName
                    tvOrderdateAdmin.text = orderDate
                    tvTotalpriceAdmin.text = totalPrice
                    tvUsernameAdmin.text = "고객명 : ${data.userId}"

                    btnCompleteAdmin.setOnClickListener {
                        onBtnClickListener.onCompleteBtnClickListener(data.id, data.token)
                    }
                    btnCancelAdmin.setOnClickListener {
                        onBtnClickListener.onCancelBtnClickListener(data.id, data.token)
                    }
                    btnFoldAdmin.setOnClickListener {
                        onBtnClickListener.onFoldBtnClickListener(orderDetailList, binding, data.isExpended)
                        data.isExpended = !data.isExpended
                    }
                    cdvContentAdmin.setOnClickListener{
                        onBtnClickListener.onContainerClickListener(orderDetailList, binding, data.isExpended)
                        data.isExpended = !data.isExpended
                    }
                }
            }
        }

        private fun formatData(data:OrderDto, orderDetailList: List<OrderDetailResponse>){
            orderName = if (orderDetailList.size == 1) {
                orderDetailList[0].productName
            } else {
                "${orderDetailList[0].productName} 외 ${orderDetailList.size - 1}건"
            }

            var price = 0
            val decimalFormat = DecimalFormat("#,###")
            for (i in 0 until orderDetailList.size) {
                price += orderDetailList[i].totalPrice
            }
            totalPrice = "총 가격 : ${decimalFormat.format(price)} 원"

            val dateFormat = SimpleDateFormat("MM월 dd일 HH:mm")
            orderDate = dateFormat.format(data.orderTime)
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

    interface OnBtnClickListener {
        fun onFoldBtnClickListener(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean)
        fun onContainerClickListener(orderDetailList: List<OrderDetailResponse>, binding: ItemListAdminBinding, isExpended: Boolean)
        fun onCompleteBtnClickListener(orderId: Int, token: String)
        fun onCancelBtnClickListener(orderId: Int, token: String)
    }
}