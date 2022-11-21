package com.ssafy.silencelake.fragment.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.silencelake.databinding.ItemListRecommendedmenuBinding
import com.ssafy.silencelake.dto.ProductDto

class RecommendedMenuAdapter(val context: Context): RecyclerView.Adapter<RecommendedMenuAdapter.RecommendedMenuViewHolder>() {
    var itemlist = mutableListOf<ProductDto>()
    lateinit var onClickRecommendedItem: OnClickRecommendedItem
    inner class RecommendedMenuViewHolder(val binding: ItemListRecommendedmenuBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: ProductDto){
            var resId = context.resources.getIdentifier(data.img, "drawable", context.packageName)
            binding.imgItemListRecommendedmenu.setImageResource(resId)
            binding.tvItemListRecommendedmenu.text = data.name
            binding.imgItemListRecommendedmenu.setOnClickListener {
                onClickRecommendedItem.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMenuViewHolder {
        val view = ItemListRecommendedmenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedMenuViewHolder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int = itemlist.size
}