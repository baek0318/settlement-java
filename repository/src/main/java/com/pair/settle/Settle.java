package com.pair.settle;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner-id")
    private Long ownerId;

    private LocalDateTime dateTime;

    private int amount;

    @Builder
    public Settle(Long id, Long ownerId, LocalDateTime dateTime, int amount) {
        this.id = id;
        this.ownerId = ownerId;
        this.dateTime = dateTime;
        this.amount = amount;
    }
}
