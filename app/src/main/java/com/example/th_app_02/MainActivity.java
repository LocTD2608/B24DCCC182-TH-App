package com.example.th_app_02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartManager = new CartManager(this);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        setupBottomNavigation();

        // Default to HomeFragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
        }

        updateCartBadgeCount();
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(new HomeFragment(), false);
                return true;
            } else if (itemId == R.id.nav_search) {
                loadFragment(new SearchFragment(), false);
                return true;
            } else if (itemId == R.id.nav_cart) {
                loadFragment(new CartFragment(), false);
                return true;
            } else if (itemId == R.id.nav_favorite) {
                loadFragment(new FavoriteFragment(), false);
                return true;
            }
            return false;
        });
    }

    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void navigateToHome() {
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    public void navigateToSearch() {
        bottomNavigation.setSelectedItemId(R.id.nav_search);
    }

    public void navigateToCart() {
        bottomNavigation.setSelectedItemId(R.id.nav_cart);
    }

    public void openDetailFragment(Food food) {
        DetailFragment detailFragment = DetailFragment.newInstance(food);
        loadFragment(detailFragment, true);
    }

    public void updateCartBadgeCount() {
        if (bottomNavigation != null && cartManager != null) {
            int count = cartManager.getCartCount();
            if (count > 0) {
                BadgeDrawable badge = bottomNavigation.getOrCreateBadge(R.id.nav_cart);
                badge.setVisible(true);
                badge.setNumber(count);
                badge.setBackgroundColor(getResources().getColor(R.color.primary, null));
                badge.setBadgeTextColor(getResources().getColor(R.color.white, null));
            } else {
                bottomNavigation.removeBadge(R.id.nav_cart);
            }
        }

        // Also update HomeFragment if active
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).updateCartBadge();
        }
    }
}
