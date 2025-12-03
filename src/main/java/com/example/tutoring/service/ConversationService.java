package com.example.tutoring.service;

import com.example.tutoring.entity.Conversation;
import com.example.tutoring.entity.Message;
import com.example.tutoring.repository.ConversationRepository;
import com.example.tutoring.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Conversation createConversation(Long userId, String title) {
        Conversation conversation = new Conversation();
        conversation.setSessionId(UUID.randomUUID().toString());
        conversation.setUserId(userId);
        conversation.setTitle(title);
        return conversationRepository.save(conversation);
    }

    public Optional<Conversation> findBySessionId(String sessionId) {
        return conversationRepository.findBySessionId(sessionId);
    }

    public List<Conversation> getUserConversations(Long userId) {
        return conversationRepository.findByUserId(userId);
    }

    public List<Conversation> getActiveUserConversations(Long userId) {
        return conversationRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public Conversation updateConversationTitle(String sessionId, String title) {
        Optional<Conversation> conversationOpt = conversationRepository.findBySessionId(sessionId);
        if (conversationOpt.isPresent()) {
            Conversation conversation = conversationOpt.get();
            conversation.setTitle(title);
            return conversationRepository.save(conversation);
        }
        return null;
    }

    public void deactivateConversation(String sessionId) {
        Optional<Conversation> conversationOpt = conversationRepository.findBySessionId(sessionId);
        if (conversationOpt.isPresent()) {
            Conversation conversation = conversationOpt.get();
            conversation.setIsActive(false);
            conversationRepository.save(conversation);
        }
    }

    public List<Message> getConversationMessages(Long conversationId) {
        return messageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
    }
}