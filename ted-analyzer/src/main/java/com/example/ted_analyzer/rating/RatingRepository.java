package com.example.ted_analyzer.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // 비디오 URL로 별점 및 리뷰를 조회
    Optional<Rating> findByVideoUrl(String videoUrl);

    // 특정 비디오의 평균 별점을 조회
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.videoUrl = :videoUrl")
    Double findAverageRatingByVideoUrl(String videoUrl);

    // 2점 이하 리뷰 중 가장 최근 2개
    List<Rating> findTop2ByVideoUrlAndRatingLessThanEqualOrderByCreatedAtDesc(String videoUrl, int rating);

    // 4점 이상 리뷰 중 가장 최근 2개
    List<Rating> findTop2ByVideoUrlAndRatingGreaterThanEqualOrderByCreatedAtDesc(String videoUrl, int rating);
}