package com.unaerp.restaurantmenu.Feature.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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

            val storageReference: StorageReference = Firebase.storage.reference
            val imageRef = storageReference.child(cartItem.image)
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(binding.imageView.context).load(uri).into(binding.imageView)
            }

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

            binding.trashNutton.setOnClickListener {
                viewModel.deleteItem(cartItem)
                notifyDataSetChanged()
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

    fun getTotalPrice(): Double = cartItems.sumOf { it.unitPrice * it.quantity }

    fun updateList(newList: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newList)
        notifyDataSetChanged()
    }
}
