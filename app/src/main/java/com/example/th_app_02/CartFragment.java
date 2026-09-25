package com.example.th_app_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private RecyclerView recyclerCart;
    private CartAdapter cartAdapter;
    private TextView tvCartTotalPrice, btnClearCart;
    private View layoutCartEmpty, layoutCheckoutBar;
    private Button btnCheckout;

    private CartManager cartManager;
    private List<CartItem> cartItems;
    private NumberFormat formatter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        cartManager = new CartManager(requireContext());

        initViews(view);
        loadCartData();
        setupListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCartData();
    }

    private void initViews(View view) {
        recyclerCart = view.findViewById(R.id.recyclerCart);
        recyclerCart.setLayoutManager(new LinearLayoutManager(requireContext()));

        tvCartTotalPrice = view.findViewById(R.id.tvCartTotalPrice);
        btnClearCart = view.findViewById(R.id.btnClearCart);
        layoutCartEmpty = view.findViewById(R.id.layoutCartEmpty);
        layoutCheckoutBar = view.findViewById(R.id.layoutCheckoutBar);
        btnCheckout = view.findViewById(R.id.btnCheckout);
    }

    private void loadCartData() {
        cartItems = cartManager.getCartItems();

        if (cartAdapter == null) {
            cartAdapter = new CartAdapter(cartItems, (foodId, newQuantity) -> {
                cartManager.updateQuantity(foodId, newQuantity);
                loadCartData();
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateCartBadgeCount();
                }
            });
            recyclerCart.setAdapter(cartAdapter);
        } else {
            cartAdapter.updateList(cartItems);
        }

        updateUI();
    }

    private void updateUI() {
        if (cartItems.isEmpty()) {
            layoutCartEmpty.setVisibility(View.VISIBLE);
            recyclerCart.setVisibility(View.GONE);
            layoutCheckoutBar.setVisibility(View.GONE);
            if (btnClearCart != null) btnClearCart.setVisibility(View.GONE);
        } else {
            layoutCartEmpty.setVisibility(View.GONE);
            recyclerCart.setVisibility(View.VISIBLE);
            layoutCheckoutBar.setVisibility(View.VISIBLE);
            if (btnClearCart != null) btnClearCart.setVisibility(View.VISIBLE);

            int total = cartManager.getTotalCartPrice();
            tvCartTotalPrice.setText(formatter.format(total) + " VNĐ");
        }
    }

    private void setupListeners() {
        if (btnClearCart != null) {
            btnClearCart.setOnClickListener(v -> {
                cartManager.clearCart();
                loadCartData();
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateCartBadgeCount();
                }
                Toast.makeText(requireContext(), "Đã xóa toàn bộ giỏ hàng!", Toast.LENGTH_SHORT).show();
            });
        }

        btnCheckout.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                int total = cartManager.getTotalCartPrice();
                cartManager.clearCart();
                loadCartData();
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateCartBadgeCount();
                }
                Toast.makeText(requireContext(), "Thanh toán thành công " + formatter.format(total) + " VNĐ! Cảm ơn bạn đã lựa chọn FoodGo.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
