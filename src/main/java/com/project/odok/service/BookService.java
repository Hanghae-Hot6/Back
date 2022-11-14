package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@RequiredArgsConstructor
@Service
public class BookService {

//    @Value("${clientId}") String clientId;
//    @Value("${clientSecret}") String clientSecret;

    private final String clientId = null;
    private final String clientSecret = null;

//    public String searchResult(String keyword) {
//        String encode = Base64.getEncoder().encodeToString(keyword.getBytes(StandardCharsets.UTF_8));
//
//        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/")
//                .path("v1/search/book.json")
//                .queryParam("query", keyword)
//                .queryParam("display", 3)
//                .queryParam("start", 1)
//                .encode()
//                .build()
//                .toUri();
//
//        RestTemplate restTemplate = new RestTemplate();
//
//
//        RequestEntity<Void> req = RequestEntity
//                .get(uri)
//                .header("X-Naver-Client-Id", clientId)
//                .header("X-Naver-Client-Secret", clientSecret)
//                .build();
//
//        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
//
//        return result.getBody();
//    }

        public ResponseDto<?> searchResult(String keyword){

            String bookTitle = null;

            try {
                bookTitle = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("검색어 인코딩 실패",e);
            }

            String apiURL = "https://openapi.naver.com/v1/search/book?query=" + bookTitle + "&display=" + 5 + "&start=" + 1;


            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            String responseBody = get(apiURL,requestHeaders);

            List<Map<String, Object>> searchList = new ArrayList<>();

            try{
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
                JSONArray items = (JSONArray) jsonObject.get("items");

                for(int i = 0; i < items.size(); i++){
                    JSONObject tmp = (JSONObject) items.get(i);
                    String title = (String) tmp.get("title");
                    String link = (String) tmp.get("link");
                    String image = (String) tmp.get("image");
                    String author = (String) tmp.get("author");
                    String publisher = (String) tmp.get("publisher");
                    String isbn = (String) tmp.get("isbn");

                    Map<String, Object> bookInfo = new HashMap<>();

                    bookInfo.put("image", image);
                    bookInfo.put("title", title);
                    bookInfo.put("isbn", isbn);
                    bookInfo.put("no", i);

                    searchList.add(bookInfo);

                }
            } catch (ParseException e) {
                throw new RuntimeException("Parsing Exception");
            }

            return ResponseDto.success(searchList);
        }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
