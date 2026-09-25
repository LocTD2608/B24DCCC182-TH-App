package com.example.th_app_02;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "FoodGoCartPref";
    private static final String KEY_CART = "cart_items";

    private final SharedPreferences prefs;

    public CartManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void addToCart(Food food, int quantity) {
        List<CartItem> cart = getCartItems();
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getFood().getId() == food.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new CartItem(food, quantity));
        }
        saveCart(cart);
    }

    public void updateQuantity(int foodId, int newQuantity) {
        List<CartItem> cart = getCartItems();
        List<CartItem> updatedCart = new ArrayList<>();
        for (CartItem item : cart) {
            if (item.getFood().getId() == foodId) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                    updatedCart.add(item);
                }
            } else {
                updatedCart.add(item);
            }
        }
        saveCart(updatedCart);
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartList = new ArrayList<>();
        String json = prefs.getString(KEY_CART, "[]");
        List<Food> allFood = FoodDataSource.getFoodList();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int foodId = obj.getInt("foodId");
                int qty = obj.getInt("quantity");
                for (Food food : allFood) {
                    if (food.getId() == foodId) {
                        cartList.add(new CartItem(food, qty));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public int getCartCount() {
        int count = 0;
        for (CartItem item : getCartItems()) {
            count += item.getQuantity();
        }
        return count;
    }

    public int getTotalCartPrice() {
        int total = 0;
        for (CartItem item : getCartItems()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        prefs.edit().remove(KEY_CART).apply();
    }

    private void saveCart(List<CartItem> cart) {
        JSONArray array = new JSONArray();
        try {
            for (CartItem item : cart) {
                JSONObject obj = new JSONObject();
                obj.put("foodId", item.getFood().getId());
                obj.put("quantity", item.getQuantity());
                array.put(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefs.edit().putString(KEY_CART, array.toString()).apply();
    }
}
