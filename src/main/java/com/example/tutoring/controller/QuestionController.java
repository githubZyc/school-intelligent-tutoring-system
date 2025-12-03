package com.example.tutoring.controller;

import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.tutoring.entity.Message;
import com.example.tutoring.service.AiService;
import com.example.tutoring.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private AiService aiService;

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestBody AskQuestionRequest request) {
        try {
            String answer = aiService.askQuestion(request.getQuestion());
            
            // Save question and answer to conversation
            if (request.getSessionId() != null) {
                // TODO: Save message to database
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("answer", answer);
            return ResponseEntity.ok(response);
        } catch (ApiException | NoApiKeyException e) {
            return ResponseEntity.status(500).body("Error calling AI service: " + e.getMessage());
        }
    }

    @PostMapping("/ask-with-method")
    public ResponseEntity<?> askQuestionWithMethod(@RequestBody AskQuestionWithMethodRequest request) {
        try {
            String answer = aiService.askQuestionWithMethod(request.getQuestion(), request.getMethod());
            
            // Save question and answer to conversation
            if (request.getSessionId() != null) {
                // TODO: Save message to database
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("answer", answer);
            return ResponseEntity.ok(response);
        } catch (ApiException | NoApiKeyException e) {
            return ResponseEntity.status(500).body("Error calling AI service: " + e.getMessage());
        }
    }

    @PostMapping("/ask-with-image")
    public ResponseEntity<?> askQuestionWithImage(@RequestParam("question") String question,
                                                  @RequestParam("image") MultipartFile image,
                                                  @RequestParam("sessionId") String sessionId) {
        try {
            // TODO: Process image and send to AI service
            String answer = "Image processing is not implemented yet.";
            
            // Save question and answer to conversation
            if (sessionId != null) {
                // TODO: Save message to database
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("answer", answer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing image: " + e.getMessage());
        }
    }

    // DTOs
    static class AskQuestionRequest {
        private String question;
        private String sessionId;

        // Getters and setters
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }

    static class AskQuestionWithMethodRequest {
        private String question;
        private String method;
        private String sessionId;

        // Getters and setters
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }
}