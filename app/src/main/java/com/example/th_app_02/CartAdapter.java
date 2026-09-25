package com.example.th_app_02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private final OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onQuantityChanged(int foodId, int newQuantity);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void updateList(List<CartItem> newList) {
        this.cartItems = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        Food food = item.getFood();

        holder.imgCartFood.setImageResource(food.getImageResId());
        holder.tvCartName.setText(food.getName());

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.tvCartPrice.setText(formatter.format(item.getTotalPrice()) + " VNĐ");
        holder.tvCartQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnCartMinus.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuantityChanged(food.getId(), item.getQuantity() - 1);
            }
        });

        holder.btnCartPlus.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuantityChanged(food.getId(), item.getQuantity() + 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCartFood;
        TextView tvCartName, tvCartPrice, tvCartQuantity;
        Button btnCartMinus, btnCartPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCartFood = itemView.findViewById(R.id.imgCartFood);
            tvCartName = itemView.findViewById(R.id.tvCartName);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
            btnCartMinus = itemView.findViewById(R.id.btnCartMinus);
            btnCartPlus = itemView.findViewById(R.id.btnCartPlus);
        }
    }
}
