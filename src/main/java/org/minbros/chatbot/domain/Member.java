package org.minbros.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String userId;

    @Column
    private String name;

    @Column
    private boolean isPremium = false;

    public Member(String userId) {
        this.userId = userId;
    }

    public Member(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }
}
