package com.example.th_app_02;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String name;
    private String description;
    private String ingredients;
    private int price;
    private int originalPrice;
    private double rating;
    private String deliveryTime;
    private int imageResId;
    private String promoTag;
    private String category;

    public Food(int id, String name, String description, String ingredients, int price, int originalPrice, double rating, String deliveryTime, int imageResId, String promoTag, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.originalPrice = originalPrice;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageResId = imageResId;
        this.promoTag = promoTag;
        this.category = category;
    }

    public Food(int id, String name, String description, String ingredients, int price, int imageResId) {
        this(id, name, description, ingredients, price, (int)(price * 1.2), 4.8, "15-20 phút", imageResId, "HOT", "Đồ ăn nhanh");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getPrice() {
        return price;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public double getRating() {
        return rating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPromoTag() {
        return promoTag;
    }

    public String getCategory() {
        return category;
    }
}
