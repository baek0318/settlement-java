package com.pair.settlement.owner.dto;

import com.pair.settlement.owner.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateRequest {

    private Long id;
    private String bank;
    private String bankAccount;
    private String accountHolder;

    @Builder
    public AccountUpdateRequest(Long id, Long ownerId, String bank, String bankAccount, String accountHolder) {
        this.id = id;
        this.bank = bank;
        this.bankAccount = bankAccount;
        this.accountHolder = accountHolder;
    }

    public Account toEntity() {
        Account account = Account.builder()
                .bank(this.bank)
                .bankAccount(this.bankAccount)
                .accountHolder(this.accountHolder)
                .build();
        account.setId(id);
        return account;
    }
}
