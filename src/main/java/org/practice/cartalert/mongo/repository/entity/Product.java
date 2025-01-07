package org.practice.cartalert.mongo.repository.entity;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products") // MongoDB의 "products" 컬렉션에 매핑
@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode 포함
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴을 사용하기 위한 어노테이션
public class Product {

    @Id
    private String id;

    @Indexed(unique = true) // 상품 이름을 고유하게 설정
    private String name;

    private String description;
    private double price;
    private int stock;
    private String category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}