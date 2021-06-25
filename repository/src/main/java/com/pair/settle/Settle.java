package com.pair.settle;

import com.pair.owner.Owner;
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
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private LocalDateTime dateTime;

    private int amount;

    @Builder
    public Settle(Long id, Owner owner, LocalDateTime dateTime, int amount) {
        this.id = id;
        this.owner = owner;
        this.dateTime = dateTime;
        this.amount = amount;
    }
}
