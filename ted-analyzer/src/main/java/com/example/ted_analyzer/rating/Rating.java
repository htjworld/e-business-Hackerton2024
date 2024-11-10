package com.example.ted_analyzer.rating;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(nullable = false)
    private String videoUrl; // 비디오 URL (API 호출 시 사용하는 URL)

    @Column(nullable = false)
    private int rating; // 별점 (1~5)

    @Column(nullable = false, length = 300)
    private String review; // 리뷰 (300자 이하)

    @Column(nullable = false)
    private LocalDateTime createdAt; // 리뷰 생성 시간

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();  // 생성 시점에 자동으로 현재 시간을 설정
    }
}