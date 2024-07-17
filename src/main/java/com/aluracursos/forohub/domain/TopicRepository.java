package com.aluracursos.forohub.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndContent(String title, String content);
}
