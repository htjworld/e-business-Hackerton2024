package com.example.ted_analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class YoutubeController {

    @Autowired
    private YoutubeService youtubeService;

    @GetMapping("/video/basic_info")
    public Map<String, Object> getVideoBasicInfo(@RequestParam("url") String url) {
        String videoId = extractVideoIdFromUrl(url);

        return youtubeService.getVideoInfo(videoId);
    }

    // URL에서 비디오 ID를 추출
    private String extractVideoIdFromUrl(String url) {
        // ex. (https://www.youtube.com/watch?v=VIDEO_ID)
        String videoId = null;

        if (url.contains("v=")) {
            String[] urlParts = url.split("v=");
            videoId = urlParts[1].split("&")[0];  // "v=" 뒤의 첫 번째 파라미터만 가져옴
        } else if (url.contains("youtu.be")) {
            String[] urlParts = url.split("youtu.be/");
            videoId = urlParts[1];
        } else {
            throw new IllegalArgumentException("Invalid YouTube URL");
        }

        return videoId;
    }
}