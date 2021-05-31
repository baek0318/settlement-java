package com.pair.settlement.owner.dto;

import com.pair.settlement.owner.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerInfoResponse {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    @Builder
    public OwnerInfoResponse(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public OwnerInfoResponse(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.phoneNumber = owner.getPhoneNumber();
    }
}
