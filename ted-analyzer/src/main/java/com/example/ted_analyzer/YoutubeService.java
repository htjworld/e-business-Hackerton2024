package com.example.ted_analyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;  // YouTube API 키를 application.properties에서 가져옴

    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/videos";

    // 비디오 정보를 가져오는 메서드
    public Map<String, Object> getVideoInfo(String videoId) {
        String url = UriComponentsBuilder.fromHttpUrl(YOUTUBE_API_URL)
                .queryParam("id", videoId)
                .queryParam("key", apiKey)
                .queryParam("part", "snippet,statistics")
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    Map.class
            );

            // 응답의 본문에서 "items" 부분 추출
            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null || !responseBody.containsKey("items")) {
                throw new RuntimeException("Invalid YouTube response.");
            }

            Map<String, Object> video = ((List<Map<String, Object>>) responseBody.get("items")).get(0);
            Map<String, Object> snippet = (Map<String, Object>) video.get("snippet");
            Map<String, Object> statistics = (Map<String, Object>) video.get("statistics");

            Map<String, Object> videoData = new HashMap<>();
            videoData.put("title", snippet.get("title"));  // 영상 제목
            videoData.put("description", snippet.get("description"));  // 영상 설명
            videoData.put("channelTitle", snippet.get("channelTitle"));  // 채널 이름
            videoData.put("publishedAt", snippet.get("publishedAt"));  // 영상 업로드 날짜와 시간
            videoData.put("viewCount", statistics.get("viewCount"));  // 조회수
            videoData.put("likeCount", statistics.get("likeCount"));  // 좋아요 수
            videoData.put("commentCount", statistics.get("commentCount"));  // 댓글 수

            // 반환되는 데이터는 Map 형식으로 자동으로 JSON으로 변환
            return videoData;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch video data: " + e.getMessage());
        }
    }
}