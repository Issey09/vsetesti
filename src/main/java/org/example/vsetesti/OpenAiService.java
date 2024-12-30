package org.example.vsetesti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenAiService {

    @Value("${openai.api.link}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    public OpenAiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String sendRequest(String prompt) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(1000))
                    .build();

            // Создаем тело запроса
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("model", "openai/gpt-3.5-turbo-0125");
            requestBodyMap.put("temperature", 0.7);
            requestBodyMap.put("n", 1);
            requestBodyMap.put("max_tokens", 3000);
            requestBodyMap.put("extra_headers", Map.of("X-Title", "My App"));
            requestBodyMap.put("messages", new Object[]{
                Map.of("role", "user", "content", prompt)
            });


            String requestBody = objectMapper.writeValueAsString(requestBodyMap);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Access-Control-Allow-Origin", "http://localhost:5173")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Отправляем запрос и получаем ответ
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Проверяем статус код ответа
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Error: " + response.statusCode() + " " + response.body(); // Ошибка от сервера
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage(); // Ошибка при выполнении запроса
        }
    }
}

