package com.example.ted_analyzer.rating;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // 비디오 URL로 별점과 리뷰 추가
    @Transactional
    public String addRating(String videoUrl, int rating, String review) {
        Rating newRating = new Rating();
        newRating.setVideoUrl(videoUrl);
        newRating.setRating(rating);
        newRating.setReview(review);

        // Rating 저장
        ratingRepository.save(newRating);
        return "Rating and review added successfully!";
    }

    // 비디오 URL로 평균 별점 조회
    public Double getAverageRating(String videoUrl) {
        Double averageRating = ratingRepository.findAverageRatingByVideoUrl(videoUrl);
        if (averageRating == null) {
            return 0.0;  // 기본값으로 0.0을 반환
        }
        return averageRating;
    }

    // 2점 이하의 부정적 리뷰 조회 (최신 2개)
    public List<Rating> getNegativeReviews(String videoUrl) {
        return ratingRepository.findTop2ByVideoUrlAndRatingLessThanEqualOrderByCreatedAtDesc(videoUrl, 2);
    }

    // 4점 이상의 긍정적 리뷰 조회 (최신 2개)
    public List<Rating> getPositiveReviews(String videoUrl) {
        return ratingRepository.findTop2ByVideoUrlAndRatingGreaterThanEqualOrderByCreatedAtDesc(videoUrl, 4);
    }
}