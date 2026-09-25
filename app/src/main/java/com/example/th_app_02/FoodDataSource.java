package com.example.th_app_02;

import java.util.ArrayList;
import java.util.List;

public class FoodDataSource {
    public static List<Food> getFoodList() {
        List<Food> list = new ArrayList<>();
        list.add(new Food(1, "Hamburger bò", "Thịt bò nướng thơm lừng kèm phô mai béo ngậy và rau tươi.", "Thịt bò nướng, phô mai cheddar, xà lách, cà chua, sốt mayonnaise, vỏ bánh mì bơ.", 55000, 65000, 4.8, "15-20 phút", R.drawable.img_hamburger, "HOT", "Đồ ăn nhanh"));
        list.add(new Food(2, "Pizza hải sản", "Tôm, mực tươi sống, ớt chuông, phô mai Mozzarella dẻo thơm.", "Tôm tươi, mực ống, phô mai Mozzarella, sốt cà chua tươi, ớt chuông Đà Lạt, hành tây.", 120000, 145000, 4.9, "20-25 phút", R.drawable.img_pizza_seafood, "BEST", "Pizza"));
        list.add(new Food(3, "Mì cay Hàn Quốc", "Mì sợi dai cay nồng cấp độ 3 với hải sản và xúc xích Đức.", "Mì sợi Hàn Quốc, tôm, mực, chả cá, nấm kim châm, kim chi, xúc xích, ớt bột Hàn.", 65000, 80000, 4.7, "15-20 phút", R.drawable.img_micay, "HOT", "Mì & Phở"));
        list.add(new Food(4, "Gà rán giòn cay", "2 miếng gà rán giòn rụm sốt cay ngọt kiểu Hàn.", "Thịt đùi gà tươi, bột chiên giòn đặc biệt, sốt cay ngọt Hàn Quốc, mè trắng rang.", 75000, 90000, 4.8, "15-25 phút", R.drawable.img_garan, "Giòn rụm", "Gà Rán"));
        list.add(new Food(5, "Trà sữa đường đen", "Trà sữa kem béo đậm đà kết hợp trân châu đường đen dẻo.", "Hồng trà Đài Loan, sữa tươi thanh trùng, trân châu ngâm đường đen mỡ hành.", 35000, 45000, 4.9, "10-15 phút", R.drawable.img_trasua, "Bán chạy", "Đồ uống"));
        list.add(new Food(6, "Pizza bò băm phô mai", "Thịt bò bằm ướp tiêu đen phủ phô mai Mozzarella béo ngậy.", "Thịt bò bằm ướp tiêu đen, phô mai Mozzarella, cà chua bi, lá oregano.", 110000, 130000, 4.8, "15-20 phút", R.drawable.img_pizza_beef, "Ưu đãi", "Pizza"));
        list.add(new Food(7, "Pizza phô mai 4 vị (4-Cheese)", "Kết hợp 4 loại phô mai: Gorgonzola, Parmesan, Mozzarella & Ricotta.", "Phô mai Gorgonzola, Parmesan, Mozzarella, Ricotta, mật ong ăn kèm.", 135000, 160000, 4.7, "20-25 phút", R.drawable.img_pizza_cheese, "Mới", "Pizza"));
        return list;
    }
}
