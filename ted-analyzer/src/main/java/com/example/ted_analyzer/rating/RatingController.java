package com.example.ted_analyzer.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // 별점과 리뷰 추가
    @PostMapping("/add")
    public String addRating(@RequestParam("videoUrl") String videoUrl,
                            @RequestParam("rating") int rating,
                            @RequestParam("review") String review) {
        return ratingService.addRating(videoUrl, rating, review);
    }

    // 평균 별점 조회
    @GetMapping("/average")
    public Map<String, Object> getAverageRating(@RequestParam("videoUrl") String videoUrl) {
        Double averageRating = ratingService.getAverageRating(videoUrl);
        return Map.of("videoUrl", videoUrl, "averageRating", averageRating);
    }

    // 2점 이하의 부정적 리뷰 조회
    @GetMapping("/negative")
    public List<Rating> getNegativeReviews(@RequestParam("videoUrl") String videoUrl) {
        return ratingService.getNegativeReviews(videoUrl);
    }

    // 4점 이상의 긍정적 리뷰 조회
    @GetMapping("/positive")
    public List<Rating> getPositiveReviews(@RequestParam("videoUrl") String videoUrl) {
        return ratingService.getPositiveReviews(videoUrl);
    }
}