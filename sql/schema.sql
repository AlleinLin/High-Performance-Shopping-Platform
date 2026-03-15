-- =============================================
-- Shopping Cloud Database Schema
-- Enterprise E-commerce Platform
-- =============================================

-- Create databases for each microservice
CREATE DATABASE IF NOT EXISTS shopping_user DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopping_product DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopping_order DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopping_cart DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopping_payment DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopping_inventory DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- =============================================
-- User Service Tables
-- =============================================
USE shopping_user;

-- User table
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL COMMENT 'User ID',
    username VARCHAR(50) NOT NULL COMMENT 'Username',
    password VARCHAR(255) NOT NULL COMMENT 'Password',
    nickname VARCHAR(100) COMMENT 'Nickname',
    email VARCHAR(100) COMMENT 'Email',
    phone VARCHAR(20) COMMENT 'Phone',
    avatar VARCHAR(500) COMMENT 'Avatar URL',
    gender TINYINT DEFAULT 0 COMMENT 'Gender: 0-unknown, 1-male, 2-female',
    birthday VARCHAR(20) COMMENT 'Birthday',
    status TINYINT DEFAULT 0 COMMENT 'Status: 0-normal, 1-locked, 2-disabled',
    last_login_time DATETIME COMMENT 'Last login time',
    last_login_ip VARCHAR(50) COMMENT 'Last login IP',
    login_count INT DEFAULT 0 COMMENT 'Login count',
    remark VARCHAR(500) COMMENT 'Remark',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_email (email),
    KEY idx_phone (phone),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User table';

-- User address table
CREATE TABLE IF NOT EXISTS sys_user_address (
    id BIGINT NOT NULL COMMENT 'Address ID',
    user_id BIGINT NOT NULL COMMENT 'User ID',
    receiver_name VARCHAR(50) NOT NULL COMMENT 'Receiver name',
    receiver_phone VARCHAR(20) NOT NULL COMMENT 'Receiver phone',
    province_code VARCHAR(20) COMMENT 'Province code',
    province_name VARCHAR(50) COMMENT 'Province name',
    city_code VARCHAR(20) COMMENT 'City code',
    city_name VARCHAR(50) COMMENT 'City name',
    district_code VARCHAR(20) COMMENT 'District code',
    district_name VARCHAR(50) COMMENT 'District name',
    detail_address VARCHAR(200) NOT NULL COMMENT 'Detail address',
    postal_code VARCHAR(10) COMMENT 'Postal code',
    is_default TINYINT DEFAULT 0 COMMENT 'Is default: 0-no, 1-yes',
    tag VARCHAR(20) COMMENT 'Tag: home, company, etc.',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User address table';

-- =============================================
-- Product Service Tables
-- =============================================
USE shopping_product;

-- Category table
CREATE TABLE IF NOT EXISTS pms_category (
    id BIGINT NOT NULL COMMENT 'Category ID',
    parent_id BIGINT DEFAULT 0 COMMENT 'Parent category ID',
    name VARCHAR(50) NOT NULL COMMENT 'Category name',
    level INT DEFAULT 1 COMMENT 'Level: 1-first, 2-second, 3-third',
    icon VARCHAR(200) COMMENT 'Icon',
    image VARCHAR(500) COMMENT 'Image',
    sort INT DEFAULT 0 COMMENT 'Sort order',
    show_status TINYINT DEFAULT 1 COMMENT 'Show status: 0-hide, 1-show',
    keywords VARCHAR(200) COMMENT 'Keywords',
    description VARCHAR(500) COMMENT 'Description',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id),
    KEY idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Category table';

-- Brand table
CREATE TABLE IF NOT EXISTS pms_brand (
    id BIGINT NOT NULL COMMENT 'Brand ID',
    name VARCHAR(100) NOT NULL COMMENT 'Brand name',
    first_letter VARCHAR(1) COMMENT 'First letter',
    logo VARCHAR(500) COMMENT 'Logo URL',
    description VARCHAR(500) COMMENT 'Description',
    sort INT DEFAULT 0 COMMENT 'Sort order',
    show_status TINYINT DEFAULT 1 COMMENT 'Show status: 0-hide, 1-show',
    factory_status INT DEFAULT 0 COMMENT 'Factory status',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    KEY idx_name (name),
    KEY idx_first_letter (first_letter)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Brand table';

-- Product table
CREATE TABLE IF NOT EXISTS pms_product (
    id BIGINT NOT NULL COMMENT 'Product ID',
    name VARCHAR(200) NOT NULL COMMENT 'Product name',
    product_code VARCHAR(50) COMMENT 'Product code',
    category_id BIGINT COMMENT 'Category ID',
    brand_id BIGINT COMMENT 'Brand ID',
    main_image VARCHAR(500) COMMENT 'Main image URL',
    sub_images TEXT COMMENT 'Sub images JSON',
    price DECIMAL(10,2) NOT NULL COMMENT 'Price',
    original_price DECIMAL(10,2) COMMENT 'Original price',
    stock INT DEFAULT 0 COMMENT 'Stock quantity',
    low_stock INT DEFAULT 10 COMMENT 'Low stock threshold',
    sales INT DEFAULT 0 COMMENT 'Sales count',
    unit VARCHAR(20) COMMENT 'Unit',
    weight DECIMAL(10,2) COMMENT 'Weight(kg)',
    sort INT DEFAULT 0 COMMENT 'Sort order',
    status TINYINT DEFAULT 0 COMMENT 'Status: 0-draft, 1-pending, 2-on shelf, 3-off shelf, 4-deleted',
    brief VARCHAR(500) COMMENT 'Brief description',
    description LONGTEXT COMMENT 'Product description HTML',
    keywords VARCHAR(200) COMMENT 'Keywords',
    note VARCHAR(500) COMMENT 'Note',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    UNIQUE KEY uk_product_code (product_code),
    KEY idx_category_id (category_id),
    KEY idx_brand_id (brand_id),
    KEY idx_name (name),
    KEY idx_status (status),
    KEY idx_sales (sales),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Product table';

-- SKU table
CREATE TABLE IF NOT EXISTS pms_sku (
    id BIGINT NOT NULL COMMENT 'SKU ID',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    sku_code VARCHAR(50) COMMENT 'SKU code',
    name VARCHAR(200) COMMENT 'SKU name',
    price DECIMAL(10,2) NOT NULL COMMENT 'Price',
    stock INT DEFAULT 0 COMMENT 'Stock quantity',
    low_stock INT DEFAULT 10 COMMENT 'Low stock threshold',
    sales INT DEFAULT 0 COMMENT 'Sales count',
    image VARCHAR(500) COMMENT 'Image URL',
    spec_values JSON COMMENT 'Specification values',
    status TINYINT DEFAULT 1 COMMENT 'Status: 0-disabled, 1-enabled',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    KEY idx_product_id (product_id),
    KEY idx_sku_code (sku_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='SKU table';

-- =============================================
-- Order Service Tables
-- =============================================
USE shopping_order;

-- Order table
CREATE TABLE IF NOT EXISTS oms_order (
    id BIGINT NOT NULL COMMENT 'Order ID',
    order_no VARCHAR(50) NOT NULL COMMENT 'Order number',
    user_id BIGINT NOT NULL COMMENT 'User ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT 'Total amount',
    payment_amount DECIMAL(10,2) NOT NULL COMMENT 'Payment amount',
    discount_amount DECIMAL(10,2) DEFAULT 0 COMMENT 'Discount amount',
    freight_amount DECIMAL(10,2) DEFAULT 0 COMMENT 'Freight amount',
    coupon_id BIGINT COMMENT 'Coupon ID',
    coupon_amount DECIMAL(10,2) DEFAULT 0 COMMENT 'Coupon amount',
    status TINYINT DEFAULT 0 COMMENT 'Status: 0-pending payment, 1-paid, 2-pending shipment, 3-shipped, 4-delivered, 5-cancelled, 6-refunding, 7-refunded, 8-closed',
    payment_method TINYINT COMMENT 'Payment method: 1-Alipay, 2-WeChat, 3-Bank card, 4-Balance',
    payment_time DATETIME COMMENT 'Payment time',
    payment_transaction_id VARCHAR(100) COMMENT 'Payment transaction ID',
    delivery_time DATETIME COMMENT 'Delivery time',
    delivery_company VARCHAR(50) COMMENT 'Delivery company',
    tracking_number VARCHAR(100) COMMENT 'Tracking number',
    receive_time DATETIME COMMENT 'Receive time',
    receiver_name VARCHAR(50) NOT NULL COMMENT 'Receiver name',
    receiver_phone VARCHAR(20) NOT NULL COMMENT 'Receiver phone',
    receiver_province VARCHAR(50) COMMENT 'Receiver province',
    receiver_city VARCHAR(50) COMMENT 'Receiver city',
    receiver_district VARCHAR(50) COMMENT 'Receiver district',
    receiver_address VARCHAR(200) NOT NULL COMMENT 'Receiver address',
    receiver_zip_code VARCHAR(10) COMMENT 'Receiver zip code',
    order_type TINYINT DEFAULT 0 COMMENT 'Order type: 0-normal, 1-group buy, 2-flash sale',
    source_type TINYINT DEFAULT 0 COMMENT 'Source type: 0-PC, 1-H5, 2-APP, 3-mini program',
    remark VARCHAR(500) COMMENT 'Remark',
    auto_confirm_day INT DEFAULT 7 COMMENT 'Auto confirm days',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Order table';

-- Order item table
CREATE TABLE IF NOT EXISTS oms_order_item (
    id BIGINT NOT NULL COMMENT 'Order item ID',
    order_id BIGINT NOT NULL COMMENT 'Order ID',
    order_no VARCHAR(50) NOT NULL COMMENT 'Order number',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    product_name VARCHAR(200) NOT NULL COMMENT 'Product name',
    product_code VARCHAR(50) COMMENT 'Product code',
    product_image VARCHAR(500) COMMENT 'Product image',
    sku_id BIGINT COMMENT 'SKU ID',
    sku_name VARCHAR(200) COMMENT 'SKU name',
    sku_code VARCHAR(50) COMMENT 'SKU code',
    price DECIMAL(10,2) NOT NULL COMMENT 'Unit price',
    quantity INT NOT NULL COMMENT 'Quantity',
    total_amount DECIMAL(10,2) NOT NULL COMMENT 'Total amount',
    discount_amount DECIMAL(10,2) DEFAULT 0 COMMENT 'Discount amount',
    real_amount DECIMAL(10,2) NOT NULL COMMENT 'Real amount',
    gift_point INT DEFAULT 0 COMMENT 'Gift point',
    gift_growth INT DEFAULT 0 COMMENT 'Gift growth',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    create_by BIGINT COMMENT 'Creator',
    update_by BIGINT COMMENT 'Updater',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    KEY idx_order_no (order_no),
    KEY idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Order item table';

-- =============================================
-- Cart Service Tables
-- =============================================
USE shopping_cart;

-- Cart item table
CREATE TABLE IF NOT EXISTS cart_item (
    id BIGINT NOT NULL COMMENT 'Cart item ID',
    user_id BIGINT NOT NULL COMMENT 'User ID',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    sku_id BIGINT COMMENT 'SKU ID',
    quantity INT NOT NULL DEFAULT 1 COMMENT 'Quantity',
    checked TINYINT DEFAULT 1 COMMENT 'Checked: 0-no, 1-yes',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_product_sku (user_id, product_id, sku_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Cart item table';

-- =============================================
-- Payment Service Tables
-- =============================================
USE shopping_payment;

-- Payment record table
CREATE TABLE IF NOT EXISTS payment_record (
    id BIGINT NOT NULL COMMENT 'Payment ID',
    order_id BIGINT NOT NULL COMMENT 'Order ID',
    order_no VARCHAR(50) NOT NULL COMMENT 'Order number',
    user_id BIGINT NOT NULL COMMENT 'User ID',
    amount DECIMAL(10,2) NOT NULL COMMENT 'Payment amount',
    payment_method TINYINT NOT NULL COMMENT 'Payment method',
    status TINYINT DEFAULT 0 COMMENT 'Status: 0-pending, 1-processing, 2-success, 3-failed, 4-timeout, 5-cancelled',
    transaction_id VARCHAR(100) COMMENT 'Transaction ID',
    pay_time DATETIME COMMENT 'Pay time',
    callback_time DATETIME COMMENT 'Callback time',
    callback_content TEXT COMMENT 'Callback content',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    PRIMARY KEY (id),
    UNIQUE KEY uk_transaction_id (transaction_id),
    KEY idx_order_id (order_id),
    KEY idx_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Payment record table';

-- =============================================
-- Inventory Service Tables
-- =============================================
USE shopping_inventory;

-- Inventory table
CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT NOT NULL COMMENT 'Inventory ID',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    sku_id BIGINT COMMENT 'SKU ID',
    total_stock INT DEFAULT 0 COMMENT 'Total stock',
    available_stock INT DEFAULT 0 COMMENT 'Available stock',
    locked_stock INT DEFAULT 0 COMMENT 'Locked stock',
    low_stock_threshold INT DEFAULT 10 COMMENT 'Low stock threshold',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    version INT DEFAULT 1 COMMENT 'Version for optimistic locking',
    PRIMARY KEY (id),
    UNIQUE KEY uk_product_sku (product_id, sku_id),
    KEY idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Inventory table';

-- Inventory lock log table
CREATE TABLE IF NOT EXISTS inventory_lock_log (
    id BIGINT NOT NULL COMMENT 'Lock ID',
    order_no VARCHAR(50) NOT NULL COMMENT 'Order number',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    sku_id BIGINT COMMENT 'SKU ID',
    quantity INT NOT NULL COMMENT 'Locked quantity',
    status TINYINT DEFAULT 0 COMMENT 'Status: 0-locked, 1-unlocked, 2-deducted',
    lock_time DATETIME COMMENT 'Lock time',
    unlock_time DATETIME COMMENT 'Unlock time',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    deleted TINYINT DEFAULT 0 COMMENT 'Delete flag',
    PRIMARY KEY (id),
    KEY idx_order_no (order_no),
    KEY idx_product_sku (product_id, sku_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Inventory lock log table';
