package com.pair.owner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private Owner owner;

    private String bank;

    private String bankAccount;

    private String accountHolder;

    @Builder
    public Account(Owner owner, String bank, String bankAccount, String accountHolder) {
        this.owner = owner;
        this.bank = bank;
        this.bankAccount = bankAccount;
        this.accountHolder = accountHolder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
