package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MyappApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MyappApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("App started 🚀");

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> request = new HashMap<>();
        request.put("name", "Akshat Rampure");
        request.put("regNo", "ADT24SOCBD019");   // 👉 CHANGE THIS
        request.put("email", "akshatrampure45@gmail.com");

        Map response = restTemplate.postForObject(url, request, Map.class);

        System.out.println(response);
		String webhook = (String) response.get("webhook");
String token = (String) response.get("accessToken");

// for now dummy SQL
String finalQuery = "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT FROM EMPLOYEE e1 JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME ORDER BY e1.EMP_ID DESC;";

// headers
org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
headers.set("Authorization", token);
headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

// body
Map<String, String> body = new HashMap<>();
body.put("finalQuery", finalQuery);

// request
org.springframework.http.HttpEntity<Map<String, String>> entity =
        new org.springframework.http.HttpEntity<>(body, headers);

// send to webhook
restTemplate.postForObject(webhook, entity, String.class);

System.out.println("Answer sent ✅");
    }
}