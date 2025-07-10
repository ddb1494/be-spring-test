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
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProjectsController {

    @GetMapping("/projects")
    public List<Map<String, Object>> getProjects(
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order) throws IOException {

        // JSON 파일 읽기
        String projectsPath = "src/main/resources/data/projects.json";
        String usersPath = "src/main/resources/data/users.json";

        String projectsContent = new String(Files.readAllBytes(Paths.get(projectsPath)));
        String usersContent = new String(Files.readAllBytes(Paths.get(usersPath)));

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> projects = objectMapper.readValue(projectsContent, new TypeReference<List<Map<String, Object>>>() {});
        List<Map<String, Object>> users = objectMapper.readValue(usersContent, new TypeReference<List<Map<String, Object>>>() {});

        // 정렬 처리
        if (sort.equals("id") || sort.equals("name")) {
            projects.sort(Comparator.comparing(project -> project.get(sort).toString()));
            if (order.equalsIgnoreCase("desc")) {
                Collections.reverse(projects);
            }
        }

        // 페이징 처리
        int startIndex = (page - 1) * count;
        int endIndex = Math.min(startIndex + count, projects.size());

        if (startIndex >= projects.size()) {
            return List.of(); // 빈 리스트 반환
        }

        List<Map<String, Object>> paginatedProjects = projects.subList(startIndex, endIndex);

        // memberIds를 members로 변환
        for (Map<String, Object> project : paginatedProjects) {
            List<Integer> memberIds = (List<Integer>) project.get("memberIds");
            List<Map<String, Object>> members = users.stream()
                    .filter(user -> memberIds.contains(user.get("id")))
                    .collect(Collectors.toList());
            project.put("members", members);
            project.remove("memberIds");
        }

        return paginatedProjects;
    }
}
