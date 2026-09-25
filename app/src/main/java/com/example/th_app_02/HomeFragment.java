package com.example.th_app_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerHomeFoodList;
    private FoodAdapter foodAdapter;
    private List<Food> allFoodList;
    private List<Food> displayedFoodList;

    private TextView chipAllCategory, chipFastFood, chipPizza, chipDrink, chipNoodle, chipChicken;
    private TextView chipSortDefault, chipSortPriceAsc, chipSortPriceDesc, chipSortRating;
    private View boxQuickSearch, fabQuickCart;
    private TextView tvCartBadgeCount;

    private String selectedCategory = "Tất cả";
    private int selectedSort = 0; // 0: Default, 1: Asc, 2: Desc, 3: Rating

    private CartManager cartManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(requireContext()).inflate(R.layout.fragment_home, container, false);

        cartManager = new CartManager(requireContext());

        initViews(view);
        loadData();
        setupCategoryListeners();
        setupSortListeners();
        setupOtherListeners(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void initViews(View view) {
        recyclerHomeFoodList = view.findViewById(R.id.recyclerHomeFoodList);
        recyclerHomeFoodList.setLayoutManager(new LinearLayoutManager(requireContext()));

        chipAllCategory = view.findViewById(R.id.chipAllCategory);
        chipFastFood = view.findViewById(R.id.chipFastFood);
        chipPizza = view.findViewById(R.id.chipPizza);
        chipDrink = view.findViewById(R.id.chipDrink);
        chipNoodle = view.findViewById(R.id.chipNoodle);
        chipChicken = view.findViewById(R.id.chipChicken);

        chipSortDefault = view.findViewById(R.id.chipSortDefault);
        chipSortPriceAsc = view.findViewById(R.id.chipSortPriceAsc);
        chipSortPriceDesc = view.findViewById(R.id.chipSortPriceDesc);
        chipSortRating = view.findViewById(R.id.chipSortRating);

        boxQuickSearch = view.findViewById(R.id.boxQuickSearch);
        fabQuickCart = view.findViewById(R.id.fabQuickCart);
        tvCartBadgeCount = view.findViewById(R.id.tvCartBadgeCount);
    }

    private void loadData() {
        allFoodList = FoodDataSource.getFoodList();
        displayedFoodList = new ArrayList<>(allFoodList);

        foodAdapter = new FoodAdapter(displayedFoodList, new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                openDetail(food);
            }

            @Override
            public void onDetailClick(Food food) {
                openDetail(food);
            }
        });
        recyclerHomeFoodList.setAdapter(foodAdapter);
        updateCartBadge();
    }

    private void setupCategoryListeners() {
        TextView[] categoryChips = {chipAllCategory, chipFastFood, chipPizza, chipDrink, chipNoodle, chipChicken};
        String[] categoryNames = {"Tất cả", "Đồ ăn nhanh", "Pizza", "Đồ uống", "Mì & Phở", "Gà Rán"};

        for (int i = 0; i < categoryChips.length; i++) {
            final int index = i;
            if (categoryChips[i] != null) {
                categoryChips[i].setOnClickListener(v -> {
                    selectedCategory = categoryNames[index];
                    updateCategoryChipUI(categoryChips, index);
                    applyFilterAndSort();
                });
            }
        }
    }

    private void updateCategoryChipUI(TextView[] chips, int selectedIndex) {
        for (int i = 0; i < chips.length; i++) {
            if (chips[i] != null) {
                if (i == selectedIndex) {
                    chips[i].setBackgroundResource(R.drawable.bg_green_button);
                    chips[i].setTextColor(getResources().getColor(R.color.white, null));
                } else {
                    chips[i].setBackgroundResource(R.drawable.bg_category_chip);
                    chips[i].setTextColor(getResources().getColor(R.color.text_primary, null));
                }
            }
        }
    }

    private void setupSortListeners() {
        TextView[] sortChips = {chipSortDefault, chipSortPriceAsc, chipSortPriceDesc, chipSortRating};

        for (int i = 0; i < sortChips.length; i++) {
            final int index = i;
            if (sortChips[i] != null) {
                sortChips[i].setOnClickListener(v -> {
                    selectedSort = index;
                    updateSortChipUI(sortChips, index);
                    applyFilterAndSort();
                });
            }
        }
    }

    private void updateSortChipUI(TextView[] chips, int selectedIndex) {
        for (int i = 0; i < chips.length; i++) {
            if (chips[i] != null) {
                if (i == selectedIndex) {
                    chips[i].setBackgroundResource(R.drawable.bg_green_button);
                    chips[i].setTextColor(getResources().getColor(R.color.white, null));
                } else {
                    chips[i].setBackgroundResource(R.drawable.bg_category_chip);
                    chips[i].setTextColor(getResources().getColor(R.color.text_primary, null));
                }
            }
        }
    }

    private void applyFilterAndSort() {
        displayedFoodList.clear();

        // Filter by category
        if (selectedCategory.equals("Tất cả")) {
            displayedFoodList.addAll(allFoodList);
        } else {
            for (Food food : allFoodList) {
                if (food.getCategory().equalsIgnoreCase(selectedCategory)) {
                    displayedFoodList.add(food);
                }
            }
        }

        // Sort by price or rating
        if (selectedSort == 1) { // Price Asc
            Collections.sort(displayedFoodList, Comparator.comparingInt(Food::getPrice));
        } else if (selectedSort == 2) { // Price Desc
            Collections.sort(displayedFoodList, (f1, f2) -> Integer.compare(f2.getPrice(), f1.getPrice()));
        } else if (selectedSort == 3) { // Rating Desc
            Collections.sort(displayedFoodList, (f1, f2) -> Double.compare(f2.getRating(), f1.getRating()));
        }

        foodAdapter.notifyDataSetChanged();
    }

    private void setupOtherListeners(View view) {
        if (boxQuickSearch != null) {
            boxQuickSearch.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToSearch();
                }
            });
        }

        if (fabQuickCart != null) {
            fabQuickCart.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToCart();
                }
            });
        }
    }

    public void updateCartBadge() {
        if (tvCartBadgeCount != null && cartManager != null) {
            int count = cartManager.getCartCount();
            tvCartBadgeCount.setText(String.valueOf(count));
        }
    }

    private void openDetail(Food food) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openDetailFragment(food);
        }
    }
}
