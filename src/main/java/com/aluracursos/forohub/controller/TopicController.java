package com.aluracursos.forohub.controller;

import com.alura.ForoHub_challenge_back_end.domain.Topic;
import com.alura.ForoHub_challenge_back_end.domain.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
@Validated
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return topicRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Topic> createTopic(@Valid @RequestBody Topic topic) {
        // Verificar si ya existe un tópico con el mismo título y contenido
        boolean exists = topicRepository.existsByTitleAndContent(topic.getTitle(), topic.getContent());
        if (exists) {
            return ResponseEntity.status(409).build(); // 409 Conflict
        }

        topic.setCreatedAt(LocalDateTime.now());
        topic.setUpdatedAt(LocalDateTime.now());
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.ok(savedTopic);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @Valid @RequestBody Topic topicDetails) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();

            // Verificar si ya existe otro tópico con el mismo título y contenido
            boolean exists = topicRepository.existsByTitleAndContent(topicDetails.getTitle(), topicDetails.getContent());
            if (exists && (!topic.getTitle().equals(topicDetails.getTitle()) || !topic.getContent().equals(topicDetails.getContent()))) {
                return ResponseEntity.status(409).build(); // 409 Conflict
            }

            topic.setTitle(topicDetails.getTitle());
            topic.setContent(topicDetails.getContent());
            topic.setStatus(topicDetails.getStatus());
            topic.setAuthor(topicDetails.getAuthor());
            topic.setCourse(topicDetails.getCourse());
            topic.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(topicRepository.save(topic));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteTopic(@PathVariable Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
