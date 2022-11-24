package com.ssafy.silencelake.fragment.main.menu

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.ProductMenuItemBinding
import com.ssafy.silencelake.dto.ProductDto
import com.ssafy.silencelake.util.ApplicationClass

private const val TAG = "ProductMenuAdapter_싸피"

class ProductMenuAdapter(val context: Context, var list: List<ProductDto>) :
    RecyclerView.Adapter<ProductMenuAdapter.ViewHolder>() {
    lateinit var productMenuItemClickListener: ProductMenuItemClickListener

    interface ProductMenuItemClickListener {
        fun onClick(id: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProductMenu = itemView.findViewById<ImageView>(R.id.img_product_menu)
        val productNameKor = itemView.findViewById<TextView>(R.id.text_name_kor_menu)
        val productNameEng = itemView.findViewById<TextView>(R.id.text_name_eng_menu)
        fun bindInfo(item: ProductDto) {
            Glide.with(itemView)
                .load(item.img)
                .into(imgProductMenu)
            productNameKor.text = item.name
            productNameEng.text = item.nameEng
            itemView.setOnClickListener {
                productMenuItemClickListener.onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductMenuItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindInfo(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}