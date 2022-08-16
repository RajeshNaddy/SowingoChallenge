package com.neosoft.sowingo.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neosoft.sowingo.R
import com.neosoft.sowingo.databinding.ProductItemBinding
import com.neosoft.sowingo.model.Hits
import java.util.*

class ProductsAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var productList = ArrayList<Hits>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.apply {
            tvProductDec.text = product.description
            tvActualPrice.text = "${'$'}" + product.vendorInventory[0].list_price
            tvDiscountPrice.text = "${'$'}" + product.vendorInventory[0].price
            tvActualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            Glide.with(holder.itemView.context).load(product.mainImage)
                .into(holder.binding.ivProductImage)
            btnFavourite.setOnClickListener {
                product.isFavourite = !product.isFavourite
                setFavouriteButton(product.isFavourite, holder.binding)
            }
        }
        setFavouriteButton(product.isFavourite, holder.binding)
    }

    private fun setFavouriteButton(favourite: Boolean, binding: ProductItemBinding) {
        if (favourite) {
            binding.btnFavourite.setImageResource(R.drawable.remove_fav)
        } else
            binding.btnFavourite.setImageResource(R.drawable.add_fav)
    }

    override fun getItemCount(): Int = productList.size

    fun addProducts(products: ArrayList<Hits>) {
        this.productList.clear()
        this.productList.addAll(products)
        notifyDataSetChanged()
    }

    fun filter(text: String): ArrayList<Hits> {
        // creating a new array list to filter our data.
        var filteredlist: ArrayList<Hits> = ArrayList()

        // running a for loop to compare elements.
        val charString = text?.toString() ?: ""
        if (charString.isEmpty()) {
            filteredlist = productList
        } else {
            val filteredList = ArrayList<Hits>()
            for (item in productList) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.unitName!!.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.getDefault())) ||
                    item.description!!.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.getDefault()))
                ) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
            }
        }
        return filteredlist
    }
}

class MainViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
