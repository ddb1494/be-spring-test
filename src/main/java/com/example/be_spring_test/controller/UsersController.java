package com.example.be_spring_test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UsersController {

    @GetMapping("/users")
    public List<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order) throws IOException {

        // JSON 파일 읽기
        String jsonPath = "src/main/resources/data/users.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonPath)));

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> users = objectMapper.readValue(jsonContent, new TypeReference<List<Map<String, Object>>>() {});

        // 정렬 처리
        users.sort(Comparator.comparing(user -> user.get(sort).toString()));
        if (order.equalsIgnoreCase("desc")) {
            Collections.reverse(users);
        }

        // 페이징 처리
        int startIndex = (page - 1) * count;
        int endIndex = Math.min(startIndex + count, users.size());

        if (startIndex >= users.size()) {
            return List.of(); // 빈 리스트 반환
        }

        return users.subList(startIndex, endIndex);
    }
}
