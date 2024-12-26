CREATE TABLE orders (
                        id BIGINT PRIMARY KEY,                -- PK (Primary Key)
                        user_id BIGINT NOT NULL,
                        commerce_order_no VARCHAR(255),      -- 상거래 주문 번호
                        status VARCHAR(255),                 -- 주문 상태
                        total_price DECIMAL(10,2) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간 (기본값 현재 시간)
                        created_by VARCHAR(255)             -- 생성자
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE order_items (
                             id BIGINT PRIMARY KEY,                -- PK (Primary Key)
                             product_id BIGINT NOT NULL,             -- 상품 ID
                             commerce_order_line_no VARCHAR(255),       -- 상거래 주문 번호
                             price DECIMAL(10,2) NOT NULL,
                             quantity DECIMAL(10,2) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 생성 시간 (기본값 현재 시간)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE cart_items (
                            id BIGINT PRIMARY KEY,                -- PK (Primary Key)
                            user_id BIGINT NOT NULL,
                            product_id BIGINT NOT NULL,             -- 상품 ID
                            price DECIMAL(10,2) NOT NULL,
                            quantity DECIMAL(10,2) NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간 (기본값 현재 시간)
                            modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 수정 시간 (기본값 현재 시간)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE category (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL,
                          parent_id BIGINT,
                          depth INT NOT NULL DEFAULT 0,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 상품 테이블
CREATE TABLE product (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         category_id BIGINT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         description TEXT,
                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                         sales_start_at DATETIME,
                         sales_end_at DATETIME,
                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 상품 이미지 테이블
CREATE TABLE product_image (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               product_id BIGINT NOT NULL,
                               url VARCHAR(255) NOT NULL,
                               type VARCHAR(20) NOT NULL, -- THUMBNAIL, DETAIL
                               display_order INT NOT NULL DEFAULT 0,
                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 재고 테이블
CREATE TABLE stock (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       product_id BIGINT NOT NULL,
                       quantity INT NOT NULL DEFAULT 0,
                       safety_stock INT NOT NULL DEFAULT 0,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modified_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 재고 이력 테이블
CREATE TABLE stock_history (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               stock_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               quantity_changed INT NOT NULL,
                               type VARCHAR(20) NOT NULL, -- IN, OUT, ADJUST
                               reason VARCHAR(100),
                               before_quantity INT NOT NULL,
                               after_quantity INT NOT NULL,
                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               created_by VARCHAR(50) NOT NULL
);
