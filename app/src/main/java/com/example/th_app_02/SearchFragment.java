package com.example.th_app_02;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private ImageView btnBack, btnClearSearch;
    private EditText etSearchKeyword;
    private Button btnSearch;
    private TextView tvSearchResultCount;
    private View tvNoResult;
    private RecyclerView recyclerSearchResults;

    private List<Food> allFoodList;
    private List<Food> searchResultList;
    private FoodAdapter foodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        initViews(view);
        loadAllData();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        btnClearSearch = view.findViewById(R.id.btnClearSearch);
        etSearchKeyword = view.findViewById(R.id.etSearchKeyword);
        btnSearch = view.findViewById(R.id.btnSearch);
        tvSearchResultCount = view.findViewById(R.id.tvSearchResultCount);
        tvNoResult = view.findViewById(R.id.tvNoResult);
        recyclerSearchResults = view.findViewById(R.id.recyclerSearchResults);

        recyclerSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchResultList = new ArrayList<>();
        foodAdapter = new FoodAdapter(searchResultList, new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                openDetail(food);
            }

            @Override
            public void onDetailClick(Food food) {
                openDetail(food);
            }
        });
        recyclerSearchResults.setAdapter(foodAdapter);
    }

    private void loadAllData() {
        allFoodList = FoodDataSource.getFoodList();
        searchResultList.addAll(allFoodList);
        foodAdapter.notifyDataSetChanged();
        checkResultEmpty("");
    }

    private void setupListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToHome();
                }
            });
        }

        if (btnClearSearch != null) {
            btnClearSearch.setOnClickListener(v -> {
                etSearchKeyword.setText("");
                performSearch("");
            });
        }

        btnSearch.setOnClickListener(v -> {
            String keyword = etSearchKeyword.getText().toString().trim();
            performSearch(keyword);
        });

        etSearchKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (btnClearSearch != null) {
                    btnClearSearch.setVisibility(keyword.isEmpty() ? View.GONE : View.VISIBLE);
                }
                performSearch(keyword);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearch(String keyword) {
        searchResultList.clear();
        if (keyword.isEmpty()) {
            searchResultList.addAll(allFoodList);
        } else {
            String lowerKeyword = keyword.toLowerCase();
            for (Food food : allFoodList) {
                if (food.getName().toLowerCase().contains(lowerKeyword) ||
                    food.getDescription().toLowerCase().contains(lowerKeyword) ||
                    food.getCategory().toLowerCase().contains(lowerKeyword)) {
                    searchResultList.add(food);
                }
            }
        }
        foodAdapter.notifyDataSetChanged();
        checkResultEmpty(keyword);
    }

    private void checkResultEmpty(String keyword) {
        int count = searchResultList.size();
        if (tvSearchResultCount != null) {
            if (keyword.isEmpty()) {
                tvSearchResultCount.setText("Đang hiển thị tất cả (" + count + " món ăn)");
            } else {
                tvSearchResultCount.setText("Tìm thấy " + count + " kết quả cho \"" + keyword + "\"");
            }
        }

        if (count == 0) {
            tvNoResult.setVisibility(View.VISIBLE);
            recyclerSearchResults.setVisibility(View.GONE);
        } else {
            tvNoResult.setVisibility(View.GONE);
            recyclerSearchResults.setVisibility(View.VISIBLE);
        }
    }

    private void openDetail(Food food) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openDetailFragment(food);
        }
    }
}
