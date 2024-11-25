package com.unaerp.restaurantmenu.Feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unaerp.restaurantmenu.Domain.CartItem
import com.unaerp.restaurantmenu.databinding.ItemCartBinding

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val viewModel: CartViewModel,
    private val onUpdateTotal: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.itemName.text = cartItem.name
            binding.itemPrice.text = "R$ %.2f".format(cartItem.unitPrice)
            binding.itemQuantity.text = cartItem.quantity.toString()

            binding.increaseButton.setOnClickListener {
                cartItem.quantity++
                binding.itemQuantity.text = cartItem.quantity.toString()
                viewModel.updateItemQuantity(cartItem)
                onUpdateTotal()
                notifyDataSetChanged()
            }

            binding.decreaseButton.setOnClickListener {
                if (cartItem.quantity > 1) {
                    cartItem.quantity--
                    binding.itemQuantity.text = cartItem.quantity.toString()
                    viewModel.updateItemQuantity(cartItem)
                    onUpdateTotal()
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size


    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.unitPrice * it.quantity }
    }

    fun updateList(newList: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newList)
        notifyDataSetChanged()
    }
}
