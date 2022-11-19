package com.project.odok.service;

import com.project.odok.dto.ResponseDto;
import com.project.odok.dto.requestDto.book.BookRequestDto;
import com.project.odok.entity.BestSeller;
import com.project.odok.entity.Book;
import com.project.odok.repository.BestSellerRepository;
import com.project.odok.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.*;
import java.net.URLEncoder;
import java.util.*;


@RequiredArgsConstructor
@Service
public class BookService {

    @Value("${clientId}") String clientId;
    @Value("${clientSecret}") String clientSecret;

    private final BookRepository bookRepository;
    private final BestSellerRepository bestSellerRepository;


        public ResponseDto<?> searchResult(String keyword, String start, String display){

            String bookTitle = null;

            try {
                bookTitle = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("검색어 인코딩 실패",e);
            }

            String apiURL = "https://openapi.naver.com/v1/search/book?query=" + bookTitle  + "&start=" + start + "&display=" + display;

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
                    String pubdate = (String) tmp.get("pubdate");
                    String isbn = (String) tmp.get("isbn");
                    String description = (String) tmp.get("description");

                    if(!bookRepository.existsByIsbn(isbn)){
                        Book book = new Book(title, link, image, author, description, publisher, isbn);
                        bookRepository.save(book);
                    }

                    Map<String, Object> bookInfo = new HashMap<>();

                    bookInfo.put("image", image);
                    bookInfo.put("title", title);
                    bookInfo.put("isbn", isbn);
                    bookInfo.put("pubdate", pubdate);

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

    public ResponseDto<?> crawlBestSeller(List<BookRequestDto> bookRequestDto) {

        for (BookRequestDto book : bookRequestDto) {
            if (bookRepository.existsByIsbn(book.getIsbn())) {
                Book selectedBook = bookRepository.findByIsbn(book.getIsbn());
                BestSeller bestSeller = new BestSeller(selectedBook);
                bestSellerRepository.save(bestSeller);
            } else {
                Book selectedBook = new Book(book);
                bookRepository.save(selectedBook);
                BestSeller bestSeller = new BestSeller(selectedBook);
                bestSellerRepository.save(bestSeller);
            }
        }
        return ResponseDto.success("BestSeller 등록 완료");
    }

    public ResponseDto<?> crawlBooks(List<BookRequestDto> bookRequestDto) {
        for (BookRequestDto book : bookRequestDto) {
            if (!bookRepository.existsByIsbn(book.getIsbn())) {
                Book selectedBook = new Book(book);
                bookRepository.save(selectedBook);
            }
        }
        return ResponseDto.success("Book 등록 완료");
    }
}
