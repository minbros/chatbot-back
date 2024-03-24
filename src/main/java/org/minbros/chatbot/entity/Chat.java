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

    @Lob
    @Column
    private String message;

    @Lob
    @Column
    private String response;

    @Column
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Chat(String message, Member member) {
        this.message = message;
        this.time = LocalDateTime.now();
        this.member = member;
    }

    public Chat(String message, String response, Member member) {
        this.message = message;
        this.response = response;
        this.time = LocalDateTime.now();
        this.member = member;
    }
}
