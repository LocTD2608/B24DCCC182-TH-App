package com.example.th_app_02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Food food);
        void onDetailClick(Food food);
    }

    public FoodAdapter(List<Food> foodList, OnItemClickListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    public void updateList(List<Food> newList) {
        this.foodList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodDesc.setText(food.getDescription());
        holder.tvPromoTag.setText(food.getPromoTag());
        holder.tvFoodRating.setText(String.valueOf(food.getRating()));
        
        String metaText = food.getDeliveryTime() + " • Freeship";
        holder.tvFoodMeta.setText(metaText);

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = formatter.format(food.getPrice()) + " VNĐ";
        holder.tvFoodPrice.setText(formattedPrice);
        
        holder.imgFood.setImageResource(food.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(food);
            }
        });

        holder.btnDetail.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDetailClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList != null ? foodList.size() : 0;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName, tvFoodDesc, tvFoodPrice, tvPromoTag, tvFoodRating, tvFoodMeta, btnDetail;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDesc = itemView.findViewById(R.id.tvFoodDesc);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvPromoTag = itemView.findViewById(R.id.tvPromoTag);
            tvFoodRating = itemView.findViewById(R.id.tvFoodRating);
            tvFoodMeta = itemView.findViewById(R.id.tvFoodMeta);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
