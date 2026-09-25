package com.example.th_app_02;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ImageView btnDetailBack, btnGoHome, btnFavorite, imgDetailFood;
    private TextView tvDetailName, tvDetailPrice, tvDetailOriginalPrice, tvDetailRating, tvDetailDeliveryTime, tvDetailBadge;
    private TextView tvDetailIngredients, tvDetailDescription, tvQuantity, tvSubtotalCalc, tvTotalPrice;
    private Button btnMinus, btnPlus, btnOrder;

    private Food food;
    private int quantity = 1;
    private boolean isFavorite = false;
    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        getIncomingIntent();
        initViews();
        displayData();
        setupListeners();
    }

    private void getIncomingIntent() {
        if (getIntent() != null && getIntent().hasExtra("food_item")) {
            food = (Food) getIntent().getSerializableExtra("food_item");
        }
    }

    private void initViews() {
        btnDetailBack = findViewById(R.id.btnDetailBack);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnFavorite = findViewById(R.id.btnFavorite);
        imgDetailFood = findViewById(R.id.imgDetailFood);

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailPrice = findViewById(R.id.tvDetailPrice);
        tvDetailOriginalPrice = findViewById(R.id.tvDetailOriginalPrice);
        tvDetailRating = findViewById(R.id.tvDetailRating);
        tvDetailDeliveryTime = findViewById(R.id.tvDetailDeliveryTime);
        tvDetailBadge = findViewById(R.id.tvDetailBadge);
        tvDetailIngredients = findViewById(R.id.tvDetailIngredients);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);

        tvQuantity = findViewById(R.id.tvQuantity);
        tvSubtotalCalc = findViewById(R.id.tvSubtotalCalc);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnOrder = findViewById(R.id.btnOrder);

        // Add strike-through effect for original price
        if (tvDetailOriginalPrice != null) {
            tvDetailOriginalPrice.setPaintFlags(tvDetailOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void displayData() {
        if (food != null) {
            imgDetailFood.setImageResource(food.getImageResId());
            tvDetailName.setText(food.getName());
            tvDetailPrice.setText(formatter.format(food.getPrice()) + " VNĐ");
            tvDetailOriginalPrice.setText(formatter.format(food.getOriginalPrice()) + " VNĐ");
            
            tvDetailRating.setText("⭐ " + food.getRating() + " (128 đánh giá)");
            tvDetailDeliveryTime.setText("⏱ " + food.getDeliveryTime());
            tvDetailBadge.setText("🔥 " + food.getPromoTag());

            tvDetailIngredients.setText(food.getIngredients());
            tvDetailDescription.setText(food.getDescription());
            
            updateTotalPrice();
        }
    }

    private void setupListeners() {
        btnDetailBack.setOnClickListener(v -> finish());

        if (btnGoHome != null) {
            btnGoHome.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                isFavorite = !isFavorite;
                if (isFavorite) {
                    btnFavorite.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(DetailActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                } else {
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border);
                    Toast.makeText(DetailActivity.this, "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        btnPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        });

        btnOrder.setOnClickListener(v -> 
            Toast.makeText(DetailActivity.this, "Đặt món thành công! Cảm ơn bạn đã lựa chọn FoodGo.", Toast.LENGTH_LONG).show()
        );
    }

    private void updateTotalPrice() {
        if (food != null) {
            int total = quantity * food.getPrice();
            String formattedSingle = formatter.format(food.getPrice());
            String formattedTotal = formatter.format(total) + " VNĐ";

            tvSubtotalCalc.setText("(" + formattedSingle + " x " + quantity + ") =");
            tvTotalPrice.setText(formattedTotal);
            btnOrder.setText("Đặt món ngay • " + formattedTotal);
        }
    }
}
