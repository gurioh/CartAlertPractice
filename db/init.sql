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


