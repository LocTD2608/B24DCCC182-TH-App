package com.example.th_app_02;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailFragment extends Fragment {

    private ImageView btnDetailBack, btnGoHome, btnFavorite, imgDetailFood;
    private TextView tvDetailName, tvDetailPrice, tvDetailOriginalPrice, tvDetailRating, tvDetailDeliveryTime, tvDetailBadge;
    private TextView tvDetailIngredients, tvDetailDescription, tvQuantity, tvSubtotalCalc, tvTotalPrice;
    private Button btnMinus, btnPlus, btnOrder;

    private Food food;
    private int quantity = 1;
    private boolean isFavorite = false;
    private NumberFormat formatter;

    private CartManager cartManager;
    private FavoriteManager favoriteManager;

    public static DetailFragment newInstance(Food food) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("food_item", food);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail, container, false);

        formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        cartManager = new CartManager(requireContext());
        favoriteManager = new FavoriteManager(requireContext());

        getIncomingArguments();
        initViews(view);
        displayData();
        setupListeners();

        return view;
    }

    private void getIncomingArguments() {
        if (getArguments() != null && getArguments().containsKey("food_item")) {
            food = (Food) getArguments().getSerializable("food_item");
        }
    }

    private void initViews(View view) {
        btnDetailBack = view.findViewById(R.id.btnDetailBack);
        btnGoHome = view.findViewById(R.id.btnGoHome);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        imgDetailFood = view.findViewById(R.id.imgDetailFood);

        tvDetailName = view.findViewById(R.id.tvDetailName);
        tvDetailPrice = view.findViewById(R.id.tvDetailPrice);
        tvDetailOriginalPrice = view.findViewById(R.id.tvDetailOriginalPrice);
        tvDetailRating = view.findViewById(R.id.tvDetailRating);
        tvDetailDeliveryTime = view.findViewById(R.id.tvDetailDeliveryTime);
        tvDetailBadge = view.findViewById(R.id.tvDetailBadge);
        tvDetailIngredients = view.findViewById(R.id.tvDetailIngredients);
        tvDetailDescription = view.findViewById(R.id.tvDetailDescription);

        tvQuantity = view.findViewById(R.id.tvQuantity);
        tvSubtotalCalc = view.findViewById(R.id.tvSubtotalCalc);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        btnMinus = view.findViewById(R.id.btnMinus);
        btnPlus = view.findViewById(R.id.btnPlus);
        btnOrder = view.findViewById(R.id.btnOrder);

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

            // Check favorite state
            isFavorite = favoriteManager.isFavorite(food.getId());
            if (btnFavorite != null) {
                btnFavorite.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
            }

            updateTotalPrice();
        }
    }

    private void setupListeners() {
        if (btnDetailBack != null) {
            btnDetailBack.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        if (btnGoHome != null) {
            btnGoHome.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToHome();
                }
            });
        }

        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                if (food != null) {
                    isFavorite = favoriteManager.toggleFavorite(food.getId());
                    btnFavorite.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                    String msg = isFavorite ? "Đã thêm vào danh sách yêu thích!" : "Đã xóa khỏi danh sách yêu thích!";
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
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

        btnOrder.setOnClickListener(v -> {
            if (food != null) {
                cartManager.addToCart(food, quantity);
                Toast.makeText(requireContext(), "Đã thêm " + quantity + " " + food.getName() + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateCartBadgeCount();
                }
            }
        });
    }

    private void updateTotalPrice() {
        if (food != null) {
            int total = quantity * food.getPrice();
            String formattedSingle = formatter.format(food.getPrice());
            String formattedTotal = formatter.format(total) + " VNĐ";

            tvSubtotalCalc.setText("(" + formattedSingle + " x " + quantity + ") =");
            tvTotalPrice.setText(formattedTotal);
            btnOrder.setText("Thêm vào giỏ hàng • " + formattedTotal);
        }
    }
}
