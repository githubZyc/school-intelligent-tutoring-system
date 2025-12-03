package com.example.tutoring.repository;

import com.example.tutoring.entity.LearningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRecordRepository extends JpaRepository<LearningRecord, Long> {
    List<LearningRecord> findByUserId(Long userId);
    List<LearningRecord> findByUserIdAndTopic(Long userId, String topic);
}