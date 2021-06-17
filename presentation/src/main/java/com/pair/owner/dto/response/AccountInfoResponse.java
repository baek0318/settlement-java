package com.pair.owner.dto.response;

import com.pair.owner.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoResponse {

    private String ownerName;
    private String bank;
    private String bankAccount;
    private String accountHolder;

    @Builder
    public AccountInfoResponse(String ownerName, String bank, String bankAccount, String accountHolder) {
        this.ownerName = ownerName;
        this.bank = bank;
        this.bankAccount = bankAccount;
        this.accountHolder = accountHolder;
    }

    public AccountInfoResponse(Account account) {
        this.ownerName = account.getOwner().getName();
        this.bank = account.getBank();
        this.bankAccount = account.getBankAccount();
        this.accountHolder = account.getAccountHolder();
    }
}
