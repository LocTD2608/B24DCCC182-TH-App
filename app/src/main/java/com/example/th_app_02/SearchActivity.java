package com.example.th_app_02;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageView btnBack, btnClearSearch;
    private EditText etSearchKeyword;
    private Button btnSearch;
    private TextView tvSearchResultCount;
    private View tvNoResult;
    private RecyclerView recyclerSearchResults;

    private List<Food> allFoodList;
    private List<Food> searchResultList;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        loadAllData();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnClearSearch = findViewById(R.id.btnClearSearch);
        etSearchKeyword = findViewById(R.id.etSearchKeyword);
        btnSearch = findViewById(R.id.btnSearch);
        tvSearchResultCount = findViewById(R.id.tvSearchResultCount);
        tvNoResult = findViewById(R.id.tvNoResult);
        recyclerSearchResults = findViewById(R.id.recyclerSearchResults);

        recyclerSearchResults.setLayoutManager(new LinearLayoutManager(this));
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
        btnBack.setOnClickListener(v -> finish());

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
        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
        intent.putExtra("food_item", food);
        startActivity(intent);
    }
}
