package com.pair.owner.dto.request;

import com.pair.owner.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEnrollRequest {

    private String bank;

    private String bankAccount;

    private String accountHolder;

    @Builder
    public AccountEnrollRequest(String bank, String bankAccount, String accountHolder) {
        this.bank = bank;
        this.bankAccount = bankAccount;
        this.accountHolder = accountHolder;
    }

    public Account toEntity() {
        return Account.builder()
                .bank(bank)
                .bankAccount(bankAccount)
                .accountHolder(accountHolder)
                .build();
    }
}
