package com.example.tutoring.service;

import com.example.tutoring.entity.LearningRecord;
import com.example.tutoring.repository.LearningRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LearningService {

    @Autowired
    private LearningRecordRepository learningRecordRepository;

    public LearningRecord recordLearningAttempt(Long userId, String topic, Integer difficulty, boolean success) {
        // Check if there's already a record for this user and topic
        List<LearningRecord> existingRecords = learningRecordRepository.findByUserIdAndTopic(userId, topic);
        
        LearningRecord record;
        if (!existingRecords.isEmpty()) {
            // Update existing record
            record = existingRecords.get(0);
            record.setAttempts(record.getAttempts() + 1);
            if (success) {
                record.setSuccessCount(record.getSuccessCount() + 1);
            }
            record.setLastAttemptAt(LocalDateTime.now());
        } else {
            // Create new record
            record = new LearningRecord();
            record.setUserId(userId);
            record.setTopic(topic);
            record.setDifficulty(difficulty);
            record.setAttempts(1);
            record.setSuccessCount(success ? 1 : 0);
            record.setLastAttemptAt(LocalDateTime.now());
        }
        
        return learningRecordRepository.save(record);
    }

    public List<LearningRecord> getUserLearningRecords(Long userId) {
        return learningRecordRepository.findByUserId(userId);
    }

    public List<LearningRecord> getUserLearningRecordsByTopic(Long userId, String topic) {
        return learningRecordRepository.findByUserIdAndTopic(userId, topic);
    }
}