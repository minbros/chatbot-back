package org.minbros.chatbot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "LONGTEXT")
    private String message;

    @Column(columnDefinition = "LONGTEXT")
    private String response;

    @Column
    private LocalDateTime time;

    public Chat(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public Chat(String message, String response) {
        this.message = message;
        this.response = response;
        this.time = LocalDateTime.now();
    }
}
