package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findFirst5ByOrderByCreatedAtDesc();
    List<Message> findAllByUserName(String userName);
    List<Message> findAllByContent(String content);
}
