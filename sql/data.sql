-- =============================================
-- Shopping Cloud Initial Data
-- =============================================

USE shopping_user;

-- Insert admin user (password: admin123)
INSERT INTO sys_user (id, username, password, nickname, email, phone, gender, status, login_count) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'Administrator', 'admin@shopping.com', '13800138000', 1, 0, 0),
(2, 'test', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'Test User', 'test@shopping.com', '13800138001', 0, 0, 0);

USE shopping_product;

-- Insert categories
INSERT INTO pms_category (id, parent_id, name, level, sort, show_status) VALUES
(1, 0, 'Electronics', 1, 1, 1),
(2, 0, 'Clothing', 1, 2, 1),
(3, 0, 'Home & Garden', 1, 3, 1),
(4, 0, 'Sports', 1, 4, 1),
(5, 0, 'Books', 1, 5, 1),
(6, 1, 'Mobile Phones', 2, 1, 1),
(7, 1, 'Computers', 2, 2, 1),
(8, 1, 'Tablets', 2, 3, 1),
(9, 2, 'Men', 2, 1, 1),
(10, 2, 'Women', 2, 2, 1);

-- Insert brands
INSERT INTO pms_brand (id, name, first_letter, sort, show_status, factory_status) VALUES
(1, 'Apple', 'A', 1, 1, 1),
(2, 'Samsung', 'S', 2, 1, 1),
(3, 'Huawei', 'H', 3, 1, 1),
(4, 'Xiaomi', 'X', 4, 1, 1),
(5, 'Nike', 'N', 5, 1, 1),
(6, 'Adidas', 'A', 6, 1, 1);

-- Insert products
INSERT INTO pms_product (id, name, product_code, category_id, brand_id, main_image, price, original_price, stock, sales, status, brief, keywords) VALUES
(1, 'iPhone 15 Pro Max 256GB', 'P001', 6, 1, 'https://via.placeholder.com/400x400?text=iPhone15', 9999.00, 10999.00, 100, 500, 2, 'Latest iPhone with A17 Pro chip', 'iPhone,Apple,Smartphone'),
(2, 'iPhone 15 Pro 128GB', 'P002', 6, 1, 'https://via.placeholder.com/400x400?text=iPhone15Pro', 7999.00, 8999.00, 150, 800, 2, 'iPhone 15 Pro with titanium design', 'iPhone,Apple,Smartphone'),
(3, 'Samsung Galaxy S24 Ultra', 'P003', 6, 2, 'https://via.placeholder.com/400x400?text=GalaxyS24', 8999.00, 9999.00, 80, 300, 2, 'Samsung flagship with S Pen', 'Samsung,Galaxy,Smartphone'),
(4, 'Huawei Mate 60 Pro', 'P004', 6, 3, 'https://via.placeholder.com/400x400?text=Mate60', 6999.00, 7999.00, 200, 1000, 2, 'Huawei flagship with satellite communication', 'Huawei,Mate,Smartphone'),
(5, 'MacBook Pro 14" M3 Pro', 'P005', 7, 1, 'https://via.placeholder.com/400x400?text=MacBookPro', 14999.00, 16999.00, 50, 200, 2, 'MacBook Pro with M3 Pro chip', 'MacBook,Apple,Laptop'),
(6, 'iPad Pro 12.9" M2', 'P006', 8, 1, 'https://via.placeholder.com/400x400?text=iPadPro', 8999.00, 9999.00, 60, 150, 2, 'iPad Pro with Liquid Retina XDR display', 'iPad,Apple,Tablet'),
(7, 'Nike Air Max 270', 'P007', 9, 5, 'https://via.placeholder.com/400x400?text=NikeAirMax', 999.00, 1299.00, 500, 2000, 2, 'Comfortable running shoes', 'Nike,Shoes,Running'),
(8, 'Adidas Ultraboost 23', 'P008', 9, 6, 'https://via.placeholder.com/400x400?text=Ultraboost', 1199.00, 1499.00, 400, 1500, 2, 'Premium running shoes', 'Adidas,Shoes,Running'),
(9, 'Xiaomi 14 Ultra', 'P009', 6, 4, 'https://via.placeholder.com/400x400?text=Xiaomi14', 5999.00, 6499.00, 300, 600, 2, 'Xiaomi flagship with Leica camera', 'Xiaomi,Smartphone'),
(10, 'MacBook Air 15" M3', 'P010', 7, 1, 'https://via.placeholder.com/400x400?text=MacBookAir', 10999.00, 11999.00, 70, 180, 2, 'Thin and light laptop', 'MacBook,Apple,Laptop');

USE shopping_inventory;

-- Insert inventory data
INSERT INTO inventory (id, product_id, sku_id, total_stock, available_stock, locked_stock, low_stock_threshold) VALUES
(1, 1, NULL, 100, 100, 0, 10),
(2, 2, NULL, 150, 150, 0, 15),
(3, 3, NULL, 80, 80, 0, 8),
(4, 4, NULL, 200, 200, 0, 20),
(5, 5, NULL, 50, 50, 0, 5),
(6, 6, NULL, 60, 60, 0, 6),
(7, 7, NULL, 500, 500, 0, 50),
(8, 8, NULL, 400, 400, 0, 40),
(9, 9, NULL, 300, 300, 0, 30),
(10, 10, NULL, 70, 70, 0, 7);
