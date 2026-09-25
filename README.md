# 🍕 FoodGo - Ứng Dụng Đặt Đồ Ăn (Food Ordering App)

**Bài Tập Thực Hành Android - Mã Bài Tập: B24DCCC182-TH**

FoodGo là ứng dụng đặt đồ ăn trực tuyến trên nền tảng Android, được thiết kế theo giao diện hiện đại với tông màu **Xanh Lá & Trắng** lấy cảm hứng từ các ứng dụng giao đồ ăn hàng đầu.

---

## 📱 Các Màn Hình & Chức Năng Chính

### 1. Trang Chủ (`HomeFragment`)
- **Header & Địa chỉ**: Hiển thị chào mừng và địa chỉ giao hàng.
- **Thanh tìm kiếm nhanh**: Chuyển nhanh sang màn hình tìm kiếm món ăn.
- **Banner Ưu đãi**: Khung khuyến mãi hấp dẫn với mã giảm giá.
- **Phân loại món ăn (Category Filtering)**: Lọc danh mục món ăn (`Tất cả`, `🍔 Đồ ăn nhanh`, `🍕 Pizza`, `🥤 Đồ uống`, `🍜 Mì & Phở`, `🍗 Gà Rán`).
- **Sắp xếp theo giá (Sorting)**: Sắp xếp theo `Mặc định`, `Giá: Thấp -> Cao`, `Giá: Cao -> Thấp`, `⭐ Đánh giá cao`.
- **Danh sách món ăn (`RecyclerView`)**: Thẻ món ăn sắc nét kèm nhãn badge (`HOT`, `BEST`), thời gian giao hàng và nút xem chi tiết.
- **Nút Giỏ hàng nổi (Quick Cart FAB)**: Hiển thị badge số lượng món ăn trong giỏ hàng theo thời gian thực.

### 2. Trang Tìm Kiếm (`SearchFragment`)
- **Thanh tìm kiếm đa năng**: Nhập từ khóa để tìm kiếm món ăn theo tên, mô tả hoặc danh mục.
- **Xóa từ khóa nhanh (`Clear Button`)**: Nút `X` xóa từ khóa tức thì.
- **Thống kê kết quả**: Hiển thị số lượng món ăn tìm thấy.
- **Gợi ý khi không tìm thấy**: Giao diện hướng dẫn thông minh khi từ khóa không có kết quả.

### 3. Màn Hình Chi Tiết Món Ăn (`DetailFragment`)
- **Header an toàn**: Thiết kế Header cao chuẩn `64dp`, cố định bên dưới status bar giúp nút **Quay lại (`btnDetailBack`)** và nút **Trang chủ (`btnGoHome`)** hoạt động 100% chính xác, không bị che khuất.
- **Thông tin chi tiết**: Ảnh sản phẩm thực tế sắc nét, tên món, giá gốc gạch ngang, giá khuyến mãi, đánh giá sao, thời gian giao hàng.
- **Thành phần & Mô tả**: Danh sách nguyên liệu tươi ngon và mô tả cách chế biến.
- **Tăng giảm số lượng (Quantity Stepper)**: Nút `[-]` `[+]` cập nhật tự động tổng tiền.
- **Thêm vào giỏ hàng & Yêu thích**: Bấm nút Trái tim để lưu vào danh sách yêu thích, bấm *"Thêm vào giỏ hàng"* để cập nhật vào giỏ.

### 4. Trang Giỏ Hàng (`CartFragment`)
- **Quản lý giỏ hàng**: Danh sách các món ăn đã chọn, tùy chỉnh tăng/giảm số lượng từng món hoặc xóa tất cả.
- **Tính tổng tiền**: Tự động tính tổng tiền thanh toán toàn bộ giỏ hàng.
- **Thanh toán**: Nút *"Thanh toán ngay"* thực hiện đơn hàng và làm sạch giỏ hàng.

### 5. Trang Yêu Thích (`FavoriteFragment`)
- **Lưu trữ món ăn yêu thích**: Danh sách các món ăn người dùng đã thả tim.

---

## ⚙️ Kỹ Thuật & Kiến Trúc Sử Dụng

- **Kiến trúc Single-Activity & Fragments**: Quản lý bằng `MainActivity` kết hợp `HomeFragment`, `SearchFragment`, `DetailFragment`, `CartFragment`, `FavoriteFragment` thông qua `BottomNavigationView`.
- **Lưu trữ dữ liệu bền vững (`SharedPreferences`)**:
  - `CartManager`: Lưu giỏ hàng, số lượng và tổng tiền dưới dạng JSON string.
  - `FavoriteManager`: Lưu danh sách ID món ăn yêu thích.
- **RecyclerView & Adapter Pattern**: Binding dữ liệu mượt mà bằng `FoodAdapter` và `CartAdapter`.
- **Hình ảnh sản phẩm thực tế**: Sử dụng ảnh chất lượng cao được lưu trữ trực tiếp trong `res/drawable`.

---

## 🚀 Hướng Dẫn Tải Lên GitHub (Git Push Guide)

Nếu bạn thực hiện từ **Terminal trong Android Studio** hoặc **Git Bash**, chạy chuỗi lệnh sau:

```bash
# 1. Khởi tạo Git repository (nếu chưa khởi tạo)
git init

# 2. Thêm toàn bộ file trong dự án vào Git
git add .

# 3. Tạo commit đầu tiên
git commit -m "first commit - Complete FoodGo App"

# 4. Đổi tên nhánh mặc định thành main
git branch -M main

# 5. Kết nối tới repository trên GitHub của bạn
git remote add origin https://github.com/LocTD2608/B24DCCC182-TH.git

# 6. Tải code lên GitHub
git push -u origin main
```

> **Lưu ý:**
> - Nếu câu lệnh `git remote add origin ...` báo lỗi *`remote origin already exists`*, bạn có thể cập nhật remote bằng lệnh:
>   `git remote set-url origin https://github.com/LocTD2608/B24DCCC182-TH.git`
> - Nếu GitHub yêu cầu đăng nhập, hãy nhập **Personal Access Token (PAT)** hoặc xác thực tài khoản GitHub của bạn.
