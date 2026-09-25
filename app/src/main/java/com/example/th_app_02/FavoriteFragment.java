package com.example.th_app_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerFavorite;
    private FoodAdapter foodAdapter;
    private View layoutFavEmpty;

    private FavoriteManager favoriteManager;
    private List<Food> favoriteFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoriteManager = new FavoriteManager(requireContext());

        initViews(view);
        loadFavoriteData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteData();
    }

    private void initViews(View view) {
        recyclerFavorite = view.findViewById(R.id.recyclerFavorite);
        recyclerFavorite.setLayoutManager(new LinearLayoutManager(requireContext()));
        layoutFavEmpty = view.findViewById(R.id.layoutFavEmpty);
    }

    private void loadFavoriteData() {
        Set<String> favIds = favoriteManager.getFavoriteIds();
        favoriteFoodList = new ArrayList<>();
        for (Food food : FoodDataSource.getFoodList()) {
            if (favIds.contains(String.valueOf(food.getId()))) {
                favoriteFoodList.add(food);
            }
        }

        if (foodAdapter == null) {
            foodAdapter = new FoodAdapter(favoriteFoodList, new FoodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Food food) {
                    openDetail(food);
                }

                @Override
                public void onDetailClick(Food food) {
                    openDetail(food);
                }
            });
            recyclerFavorite.setAdapter(foodAdapter);
        } else {
            foodAdapter.updateList(favoriteFoodList);
        }

        if (favoriteFoodList.isEmpty()) {
            layoutFavEmpty.setVisibility(View.VISIBLE);
            recyclerFavorite.setVisibility(View.GONE);
        } else {
            layoutFavEmpty.setVisibility(View.GONE);
            recyclerFavorite.setVisibility(View.VISIBLE);
        }
    }

    private void openDetail(Food food) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openDetailFragment(food);
        }
    }
}
