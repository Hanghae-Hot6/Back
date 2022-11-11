package com.project.odok.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class BookService {



    public String searchResult(String keyword){
        ;
        String encode = Base64.getEncoder().encodeToString(keyword.getBytes(StandardCharsets.UTF_8));

        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/")
                .path("v1/search/book.json")
                .queryParam("query", keyword)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();


        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "나의 client ID")
                .header("X-Naver-Client-Secret", "나의 client secret key")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        return result.getBody();
    }
}
